package com.example.contactapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AddEditContact extends AppCompatActivity {

    private ImageView profile;
    private EditText nameEdit, phoneEdit, emailEdit, noteEdit;
    private FloatingActionButton fab;

    private String name, phone, email, note, id, addedTime, updateTime, image;
    private Boolean isEditMode;

    private Toolbar toolbar;

    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    // database helper
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        // init db
        dbHelper = new DBHelper(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditContact.this, MainActivity.class);
                startActivity(intent);
            }
        });

        profile = findViewById(R.id.profile);
        profile.setClipToOutline(true);

        nameEdit = findViewById(R.id.nameEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        emailEdit = findViewById(R.id.emailEdit);
        noteEdit = findViewById(R.id.noteEdit);

        fab = findViewById(R.id.fab);

        // get intent data
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if (isEditMode) {
            //set toolbar title
            toolbar.setTitle("Update Contact");

            // get the other value from intent
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            phone = intent.getStringExtra("PHONE");
            email = intent.getStringExtra("EMAIL");
            note = intent.getStringExtra("NOTE");
            addedTime = intent.getStringExtra("ADDEDTIME");
            updateTime = intent.getStringExtra("UPDATETIME");
            image = intent.getStringExtra("IMAGE");

            //set value in editText field
            nameEdit.setText(name);
            phoneEdit.setText(phone);
            emailEdit.setText(email);
            noteEdit.setText(note);

            selectedImageUri = Uri.parse(image);

            if (image.equals("null")) {
                profile.setImageResource(R.drawable.baseline_person_24);
            } else {
                profile.setImageURI(selectedImageUri);
            }
        } else {
            // add mode on
            toolbar.setTitle("Add Contact");
        }

        // добавим событие клика по кнопке для сохранения данных в базу данных
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            setProfilePic(this, selectedImageUri, profile);
                        }
                    }
                }
        );

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showImagePickerDialog();
                ImagePicker.with(AddEditContact.this).cropSquare().compress(512).maxResultSize(512, 512).createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        imagePickLauncher.launch(intent);
                        return null;
                    }
                });
            }
        });
    }

    private void setProfilePic(AddEditContact addEditContact, Uri selectedImageUri, ImageView profile) {
        Glide.with(addEditContact).load(selectedImageUri).apply(RequestOptions.centerCropTransform()).into(profile);
    }

    private void saveData() {
        // положим данные пользователя в переменные
        name = nameEdit.getText().toString();
        phone = phoneEdit.getText().toString();
        email = emailEdit.getText().toString();
        note = noteEdit.getText().toString();

        // get current time to save as added time
        String timeStamp = "" + System.currentTimeMillis();



        // проверим заполненность полей
        if (!name.isEmpty() || !phone.isEmpty() || !email.isEmpty() || !note.isEmpty()) {
            // save data, if user have only one data


            // check edit or add mode to save data in sql  /**/
            if (isEditMode) {
                // edit mode
                dbHelper.updateContact(
                        "" + id,
                        "" + selectedImageUri,
                        "" + name,
                        "" + phone,
                        "" + email,
                        "" + note,
                        "" + addedTime,
                        "" + timeStamp  // updated time will new time
                );

                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

            } else {  /**/
                // add mode
                long id = dbHelper.insertContact(  // код уже был
                        "" + selectedImageUri,
                        "" + name,
                        "" + phone,
                        "" + email,
                        "" + note,
                        "" + timeStamp,
                        "" + timeStamp
                );  // updated time will new time

                // To check insert data successfully, show a toast message
                Toast.makeText(this, "Inserted Successfully " + id, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Nothing to save...", Toast.LENGTH_SHORT).show();
        }
    }

//    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
//        Glide.with(context).load(imageUri).apply(RequestOptions.centerCropTransform()).into(imageView);
//    }

}