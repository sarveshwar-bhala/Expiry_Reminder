package com.example.expiryreminder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.expiryreminder.R;
import com.example.expiryreminder.data.MyDbHandler;
import com.example.expiryreminder.fragment.DatePickerFragment;
import com.example.expiryreminder.fragment.FragmentHome;
import com.example.expiryreminder.model.Details;

import java.text.DateFormat;
import java.util.Calendar;

public class UpdateDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView list;
    EditText title,date,descp;
    Button update;
    ImageView cal;

    String id,t,c,d,des;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        list = findViewById(R.id.listUpdate);
        title = findViewById(R.id.titleUpdate);
        date = findViewById(R.id.dateUpdate);
        descp = findViewById(R.id.descUpdate);
        cal = findViewById(R.id.calenderUpdate);
        update = findViewById(R.id.update);



        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        getAndSetIntentData();

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String d = date.getText().toString();
                String des = descp.getText().toString();

                MyDbHandler dbHandler = new MyDbHandler(getApplicationContext());
                Details details = new Details(t,d,des);
                int result = dbHandler.updateDetails(details);

                if (result>0){
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), FragmentHome.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id")&&
                getIntent().hasExtra("category")&&
                getIntent().hasExtra("title")&&
                getIntent().hasExtra("date")&&
                getIntent().hasExtra("desc")){

            id= getIntent().getStringExtra("id");
            c= getIntent().getStringExtra("category");
            t= getIntent().getStringExtra("title");
            d= getIntent().getStringExtra("date");
            des= getIntent().getStringExtra("desc");

//            cam.setImageBitmap(details.getImage());
//            id.(details.getId());
            list.setText(c);
            title.setText(t);
            date.setText(d);
            descp.setText(des);

        }else {
            Toast.makeText(getApplicationContext(), "No data",Toast.LENGTH_SHORT).show();
        }

    }
    public void onDateSet(DatePicker view, int year, int month, int Date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, Date);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

        date = findViewById(R.id.date);

        date.setText(currentDateString);
        String d=date.getText().toString();

    }
}
