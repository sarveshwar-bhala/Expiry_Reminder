package com.example.expiryreminder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.expiryreminder.data.MyDbHandler;
import com.example.expiryreminder.fragment.DatePickerFragment;
import com.example.expiryreminder.fragment.FragmentHome;
import com.example.expiryreminder.model.Details;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class Add_Item extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final int CAMERA_PERMISSSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    Spinner list;
    EditText title,date,descp;
    RadioGroup radioGroup;
    RadioButton selected;
    Button submit;
    ImageView cal,cam;
    Bitmap image;

    MyDbHandler db = new MyDbHandler(Add_Item.this);
    Details details = new Details();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);



        list = findViewById(R.id.list);
        title = findViewById(R.id.titleProduct);
        date = findViewById(R.id.date);
        descp = findViewById(R.id.desc);
        cal= findViewById(R.id.calender);
//        radioButton = findViewById(R.id.radioGroup);
        submit= findViewById(R.id.submit);
        cam= findViewById(R.id.itempic);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });


        String[] drop = {"Select Category:","Documents","Food and Beverage","Medicine","Suppliment","others"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,drop);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        

        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Category:")){
                    Toast.makeText(getApplicationContext(),"Selecting category is mandatory",Toast.LENGTH_SHORT).show();
                }else {
                    String item= parent.getItemAtPosition(position).toString();
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String t = title.getText().toString();
                            String d = date.getText().toString();
                            String des = descp.getText().toString();


                            if (!TextUtils.isEmpty(t)||!TextUtils.isEmpty(d)||!TextUtils.isEmpty(des)){
                                Details details = new Details(image,t,d,des);
                                details.setCategory(item);
                                db.addDetails(details);
                                startActivity(new Intent(getApplicationContext(), FragmentHome.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"You can't keep these empty",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setNoti(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        selected = findViewById(radioId);
        if (radioId == 1){
            Toast.makeText(this,"you selected 1",Toast.LENGTH_SHORT).show();
        }else if (radioId==2){
            Toast.makeText(this,"you selected 2",Toast.LENGTH_SHORT).show();
        }else if(radioId==3){
            Toast.makeText(this,"you selected 3",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"you selected 4",Toast.LENGTH_SHORT).show();
        }
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSSION_CODE);
        }else {
            openCamera();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==CAMERA_PERMISSSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else {
                Toast.makeText(getApplicationContext(),"Camera permission is required to use Camera",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        list= findViewById(R.id.list);
        if (requestCode==CAMERA_REQUEST_CODE){
            image = (Bitmap) data.getExtras().get("data");
            cam.setImageBitmap(image);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int Date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, Date);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

        date = findViewById(R.id.date);

        date.setText(currentDateString);
//        String d=date.getText().toString();

//        details.setDate(d);
//        db.addDetails(details);



    }
}