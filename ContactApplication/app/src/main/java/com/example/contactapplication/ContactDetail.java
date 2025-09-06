package com.example.contactapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class ContactDetail extends AppCompatActivity {

    private TextView nameTv, phoneTv, emailTv, addedTimeTv, updateTimeTv, noteTv;
    private ImageView profileTv;

    private String id;

    // database helper
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // init db
        dbHelper = new DBHelper(this);

        // get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        nameTv = findViewById(R.id.name_tv);
        phoneTv = findViewById(R.id.phone_tv);
        emailTv = findViewById(R.id.email_tv);
        addedTimeTv = findViewById(R.id.added_time_tv);
        updateTimeTv = findViewById(R.id.update_time_tv);
        noteTv = findViewById(R.id.note_tv);

        profileTv = findViewById(R.id.profile_tv);

        loadDataById();
    }

    private void loadDataById() {

        // get data from database
        // query for find data by id
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                // get data
                String name = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String image = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                String phone = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String note = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE));
                String addTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                String updateTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                //convert time to dd/mm/yy hh:mm:aa format
                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = "" + DateFormat.format("dd/MM/yy hh:mm:aa", calendar);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = "" + DateFormat.format("dd/MM/yy hh:mm:aa", calendar);

                // set data
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                noteTv.setText(note);
                addedTimeTv.setText(timeAdd);
                updateTimeTv.setText(timeUpdate);

                if (image.equals("null")) {
                    profileTv.setImageResource(R.drawable.baseline_person_24);
                } else {
                    profileTv.setImageURI(Uri.parse(image));
                }

            } while (cursor.moveToNext());
        }

        db.close();
    }
}