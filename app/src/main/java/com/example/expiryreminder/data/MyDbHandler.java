package com.example.expiryreminder.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.expiryreminder.model.Details;
import com.example.expiryreminder.params.Params;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    private Context context;
    private byte[] imageInBytes;
    public MyDbHandler(Context context) {
        super(context, Params.DATABASE_NAME, null, Params.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = " CREATE TABLE IF NOT EXISTS "+Params.TABLE_NAME+
                        "(" + Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Params.KEY_IMAGE + " TEXT, "
                        + Params.KEY_CATEGORY + " TEXT, "
                        + Params.KEY_TITLE + " TEXT, "
                        + Params.KEY_DATE + " TEXT, "
                        + Params.KEY_DESCRIPTION + " TEXT " + ")";

        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS "+Params.TABLE_NAME);
        onCreate(db);

    }

    public void addDetails(Details details){
        SQLiteDatabase db = this.getWritableDatabase();

        //to save image to database
        Bitmap imageToStoreBitmap = details.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imageInBytes= byteArrayOutputStream.toByteArray();
        //till here
        ContentValues values = new ContentValues();
        values.put(Params.KEY_IMAGE,imageInBytes);
        values.put(Params.KEY_CATEGORY,details.getCategory());
        values.put(Params.KEY_TITLE,details.getTitle());
        values.put(Params.KEY_DATE,details.getDate());
        values.put(Params.KEY_DESCRIPTION,details.getDescription());

        db.insert(Params.TABLE_NAME,null,values);
        Log.d("dbsavu","Successfully Inserted");
        db.close();
    }


    public List<Details> getAllDetails(){
        List<Details> detailsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = " SELECT * FROM " + Params.TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);

        if (cursor.moveToFirst()){
            do {
                Details details = new Details();
                //to show image to listview
                details.setId(Integer.parseInt(cursor.getString(0)));
                imageInBytes = cursor.getBlob(1);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageInBytes,0,imageInBytes.length);
                details.setImage(bitmap);
                //till here
                details.setCategory(cursor.getString(2));
                details.setTitle(cursor.getString(3));
                details.setDate(cursor.getString(4));
                details.setDescription(cursor.getString(5));

                detailsList.add(details);
            }while (cursor.moveToNext());
        }
        return detailsList;
    }

    public int deleteById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(id)});
//        db.close();
    }

    public void deleteDetails(Details details){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID + "=?",new String[]{String.valueOf(details.getId())});
        db.close();
    }

    public int updateDetails(Details details){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(Params.KEY_CATEGORY,details.getCategory());
        contentValues.put(Params.KEY_TITLE,details.getTitle());
        contentValues.put(Params.KEY_DATE,details.getDate());
        contentValues.put(Params.KEY_DESCRIPTION,details.getDescription());

        return database.update(Params.TABLE_NAME
                                , contentValues
                                ,Params.KEY_ID + "+?"
                                , new String[]{String.valueOf(details.getId())});
    }
}
