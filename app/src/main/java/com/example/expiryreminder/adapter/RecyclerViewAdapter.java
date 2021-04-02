package com.example.expiryreminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expiryreminder.R;
import com.example.expiryreminder.data.MyDbHandler;
import com.example.expiryreminder.model.Details;
import com.example.expiryreminder.UpdateDetails;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Details> detailsList;
    ImageView delete, edit;

    public RecyclerViewAdapter(Context context, List<Details> detailsList) {
        this.context = context;
        this.detailsList = detailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        delete = view.findViewById(R.id.delete);
        edit = view.findViewById(R.id.edit);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Details details = detailsList.get(position);
        holder.image.setImageBitmap(details.getImage());
        holder.Category.setText(details.getCategory());
        holder.Date.setText(details.getDate());
        holder.Desc.setText(details.getDescription());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHandler dbHandler = new MyDbHandler(context);
                if (detailsList != null) {
                    int position = dbHandler.deleteById(details.getId());
                    if (position > 0) {
                        detailsList.remove(details);
                        notifyDataSetChanged();
                        notifyItemRemoved(details.getId());
                        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                    }else {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, UpdateDetails.class);
                intent.putExtra("id",details.getId());
                intent.putExtra("category",String.valueOf(details.getCategory()));
                intent.putExtra("title",String.valueOf(details.getTitle()));
                intent.putExtra("date",String.valueOf(details.getDate()));
                intent.putExtra("desc",String.valueOf(details.getDescription()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Category;
        public TextView Date;
        public TextView Desc;
        public ImageView image,delete,edit;
//        public ImageView delete,edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.itemImage);
            Category = itemView.findViewById(R.id.category);
            Date = itemView.findViewById(R.id.dateCard);
            Desc = itemView.findViewById(R.id.descCard);
//            delete = itemView.findViewById(R.id.delete);

//            delete.setOnClickListener(this);
//            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            }
        }
    }


