package com.example.expiryreminder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expiryreminder.Add_Item;
import com.example.expiryreminder.R;
import com.example.expiryreminder.adapter.RecyclerViewAdapter;
import com.example.expiryreminder.data.MyDbHandler;
import com.example.expiryreminder.model.Details;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Details> detailsArrayList;
    private ArrayAdapter<String> arrayAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        MyDbHandler db = new MyDbHandler(getContext());
//        Details licence = new Details();
        detailsArrayList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Details> allDetails = db.getAllDetails();

        for (Details details:allDetails){
            Log.d("dbsavu","\nId: " +details.getId()
                                    + "Image: " +details.getImage()
                                    + "Category: "+details.getCategory()
                                    + "Title: "+details.getTitle()
                                    + "Date: "+details.getDate()
                                    + "Description: "+details.getDescription());
            detailsArrayList.add(details);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),detailsArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);



        FloatingActionButton add = v.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Add_Item.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
