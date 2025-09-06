package com.example.json;

import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("course_name")
    private String courseName;

    @SerializedName("price")
    private int mPrice;

    public Course(String courseName, int mPrice) {
        this.courseName = courseName;
        this.mPrice = mPrice;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", mPrice=" + mPrice +
                '}';
    }
}
