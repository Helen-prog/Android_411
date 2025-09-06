package com.example.json;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();

//        Student student = new Student("Igor", "igor@gmail.com", 18);
//
//        String json = gson.toJson(student);
//
////        System.out.println(json);
//
//        Log.d(TAG, json);

        String data = "{\"age\":18,\"email\":\"igor@gmail.com\",\"name\":\"Igor\"}";
        Student student = gson.fromJson(data, Student.class);
        Log.d(TAG, student.toString());
    }
}