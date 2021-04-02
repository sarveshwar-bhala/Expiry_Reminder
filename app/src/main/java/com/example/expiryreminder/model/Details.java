package com.example.expiryreminder.model;

import android.graphics.Bitmap;

import java.io.Serializable;


public class Details implements Serializable{
    private int id;
    private Bitmap image;
    private String category;
    private String title;
    private String date;
    private String description;

    public Details(int id,Bitmap image, String category, String title, String date, String description) {
        this.id = id;
        this.image=image;
        this.category = category;
        this.title = title;
        this.date = date;
        this.description = description;
    }



    public Details(Bitmap image, String category, String title, String date, String description) {
        this.image=image;
        this.category = category;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public Details() {
    }
    public Details(String title,String date,String description){
        this.title=title;
        this.date=date;
        this.description=description;

    }

    public Details(Bitmap image,String title, String date, String description) {
        this.image=image;
        this.title = title;
        this.date = date;
        this.description = description;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
