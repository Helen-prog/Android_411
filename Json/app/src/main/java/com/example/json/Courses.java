package com.example.json;

public class Courses {
    private String course_name;
    private int price;

    public Courses(String course_name, int price) {
        this.course_name = course_name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "course_name='" + course_name + '\'' +
                ", price=" + price +
                '}';
    }
}
