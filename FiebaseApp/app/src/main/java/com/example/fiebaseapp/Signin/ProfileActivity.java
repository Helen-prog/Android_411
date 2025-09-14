package com.example.fiebaseapp.Signin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.fiebaseapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ImageView ivUser;
    EditText etDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        ivUser = findViewById(R.id.iv_user);
        etDisplayName = findViewById(R.id.et_display_name);

        Glide.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(ivUser);
        etDisplayName.setText(mAuth.getCurrentUser().getDisplayName());

    }
}