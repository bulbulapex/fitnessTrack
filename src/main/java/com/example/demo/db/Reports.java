package com.example.demo.db;

public class Reports {
    String title;
    String duration;
    String date;

    public Reports(String title, String duration, String date) {
        this.title = title;
        this.duration = duration;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
