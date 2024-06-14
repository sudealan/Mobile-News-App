package com.example.newsappsudealan;

import java.util.Date;

public class NewsModel {

    int id;
    String title;
    String text;
    static String categoryName;
    String imageField;
    String date;

    public NewsModel(int id, String title, String text, String categoryName, String imageField, String date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.categoryName = categoryName;
        this.imageField = imageField;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageField() {
        return imageField;
    }

    public void setImageField(String imageField) {
        this.imageField = imageField;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
