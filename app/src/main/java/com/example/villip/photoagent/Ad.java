package com.example.villip.photoagent;

import android.graphics.Bitmap;


public class Ad {
    // Ads table name
    public static final String TABLE_NAME = "ads";

    // Ads Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_PLACE = "place";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATE = "date";
    public static final String KEY_IMAGE = "image";

    public static final String[] COLUMNS = {KEY_ID, KEY_PLACE, KEY_TIME, KEY_DATE, KEY_IMAGE};

    private int id;
    private String place;
    private String time;
    private String date;
    private Bitmap image;

    public Ad() {
    }

    public Ad(String place, String time, String date, Bitmap image) {
        this.place = place;
        this.time = time;
        this.date = date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "place='" + place + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", image=" + image +
                '}';
    }
}
