package com.example.volleyapp;

public class Item {
    private String name, imageUrl, year;

    public Item(String name, String imageUrl, String year) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYear() {
        return year;
    }
}
