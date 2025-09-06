package com.example.json;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ArrayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array);

        Gson gson = new Gson();

//        List<Courses> coursesList = new ArrayList<>();
//        coursesList.add(new Courses("Java", 399));
//        coursesList.add(new Courses("Kotlin", 499));
//        coursesList.add(new Courses("Android", 599));
//
//        Students students = new Students("Roman", "roman@gmail.com", 22, coursesList);
//        String json = gson.toJson(students);
//        Log.d("Test2", json);

        String json_data = " {\"age\":22,\"email\":\"roman@gmail.com\",\"list\":[{\"course_name\":\"Java\",\"price\":399},{\"course_name\":\"Kotlin\",\"price\":499},{\"course_name\":\"Android\",\"price\":599}],\"name\":\"Roman\"}";
        Students students = gson.fromJson(json_data, Students.class);
        Log.d("Test4", students.toString());

    }
}