package com.example.sqliteapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HelperActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView tvOut;
    EditText e_name, e_surname, e_year;
    Button btnAdd, btnGet, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        dbHelper = new DBHelper(this);
        tvOut = findViewById(R.id.tvOut);

        e_name = findViewById(R.id.editName);
        e_surname = findViewById(R.id.editSurname);
        e_year = findViewById(R.id.editYear);

        btnAdd = findViewById(R.id.buttonAdd);
        btnGet = findViewById(R.id.buttonGet);
        btnDel = findViewById(R.id.buttonDel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e_name.getText().toString();
                String surname = e_surname.getText().toString();
                int year = Integer.parseInt(e_year.getText().toString());

                Data data = new Data(name, surname, year);
                dbHelper.addOne(data);

                // очистим поля ввода
                e_name.setText("");
                e_surname.setText("");
                e_year.setText("");
            }
        });

    }
}