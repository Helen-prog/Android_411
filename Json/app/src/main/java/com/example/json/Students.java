package com.example.json;

import java.util.List;

public class Students {

    private String name, email;
    private int age;

    private List<Courses> list;

    public Students(String name, String email, int age, List<Courses> list) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.list = list;
    }

    @Override
    public String toString() {
        return "Students{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", list=" + list +
                '}';
    }
}
