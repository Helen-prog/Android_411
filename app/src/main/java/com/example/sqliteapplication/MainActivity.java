package com.example.sqliteapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button buttonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonClick = findViewById(R.id.buttonClick);

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER, UNIQUE(name))");
                db.execSQL("INSERT INTO users VALUES ('Olga', 22), ('Viktor', 35);");

                Cursor query = db.rawQuery("SELECT * FROM users", null);

                TextView textView = findViewById(R.id.sampleText);
                textView.setText("");

                while (query.moveToNext()){
                    String name = query.getString(0);
                    int age = query.getInt(1);
                    textView.append("Name: " + name + " Age: " + age + "\n");
                }

                query.close();
                db.close();
            }
        });

    }
}