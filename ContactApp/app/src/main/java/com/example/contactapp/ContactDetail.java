package com.example.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
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



import java.util.Locale;

public class ContactDetail extends AppCompatActivity {

    private TextView nameTv, phoneTv, emailTv, addedTv, updateTv, noteTv;
    private ImageView profileTv;

    private String id;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        nameTv = findViewById(R.id.name_tv);
        phoneTv = findViewById(R.id.phone_tv);
        emailTv = findViewById(R.id.email_tv);
        addedTv = findViewById(R.id.added_time_tv);
        updateTv = findViewById(R.id.update_time_tv);
        noteTv = findViewById(R.id.note_tv);
        profileTv = findViewById(R.id.profile_tv);

        loadDataById();
    }

    private void loadDataById() {
        String selectQuery = "SELECT * FROM " + Contacts.TABLE_NAME + " WHERE " + Contacts.C_ID + " = \"" + id + "\"";
        // SELECT * FROM CONTACT_TABLE WHERE C_ID = "id"
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                String name = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_NAME));
                String image = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_IMAGE));
                String phone = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_PHONE));
                String email = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_EMAIL));
                String note = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_NOTE));
                String addTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_ADDED_TIME));
                String updateTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Contacts.C_UPDATED_TIME));

                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = "" + DateFormat.format("dd/MM/yy HH:mm:ss", calendar.getTime());

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = "" + DateFormat.format("dd/MM/yy HH:mm:ss", calendar.getTime());

                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                noteTv.setText(note);
                addedTv.setText(timeAdd);
                updateTv.setText(timeUpdate);

                if(image.equals("null")){
                    profileTv.setImageResource(R.drawable.baseline_person_24);
                } else {
                    profileTv.setImageURI(Uri.parse(image));
                }

            } while (cursor.moveToNext());
        }
        db.close();
    }
}