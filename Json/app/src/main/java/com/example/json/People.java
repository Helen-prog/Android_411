package com.example.json;

import com.google.gson.annotations.SerializedName;

public class People {

    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("age")
    private int age;

    Course course;

    public People(String mName, String mEmail, int age, Course course) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.age = age;
        this.course = course;
    }

    @Override
    public String toString() {
        return "People{" +
                "mName='" + mName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", age=" + age +
                ", course=" + course +
                '}';
    }
}
