package com.example.saba.booklistingapp;

import android.widget.Adapter;

import java.util.Date;

public class Books {
    String title;
    String subtitle;
    String author;
    String date;

    public Books(String title, String subtitle, String author, String date){
        this.title = title;
        this.subtitle=subtitle;
        this.author = author;
        this.date= date;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }

}
