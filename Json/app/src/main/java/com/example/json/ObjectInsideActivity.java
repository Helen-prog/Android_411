package com.example.json;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

public class ObjectInsideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_inside);

        Gson gson = new Gson();

//        Course course = new Course("Java", 399);
//        People people = new People("Vadim", "vadim@gmail.com", 20, course);
//
//        String json = gson.toJson(people);
//
//        Log.d("Test", json);

        String json = "{\"age\":20,\"course\":{\"course_name\":\"Java\",\"price\":399},\"email\":\"vadim@gmail.com\",\"name\":\"Vadim\"}";
        People people = gson.fromJson(json, People.class);
        Log.d("Test1", people.toString());
    }
}