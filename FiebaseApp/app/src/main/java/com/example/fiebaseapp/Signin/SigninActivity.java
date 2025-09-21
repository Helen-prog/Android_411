package com.example.fiebaseapp.Signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fiebaseapp.HomeActivity;
import com.example.fiebaseapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText etSignInEmail, etSignInPassword;
    Button btnSignInUser;

    TextView tvVerifyEmail, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        etSignInEmail = findViewById(R.id.et_signin_email);
        etSignInPassword = findViewById(R.id.et_signin_password);
        btnSignInUser = findViewById(R.id.btn_signin_user);
        tvVerifyEmail = findViewById(R.id.tv_verify_email);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);

        if (mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        btnSignInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSignInEmail.getText().toString().isEmpty()){
                    etSignInEmail.setError("Please Enter Email");
                } else if (etSignInPassword.getText().toString().isEmpty()){
                    etSignInPassword.setError("Please Enter Password");
                } else {
                    mAuth.signInWithEmailAndPassword(etSignInEmail.getText().toString(), etSignInPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if(mAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SigninActivity.this, "Please Verify Email", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SigninActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        tvVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() == null){
                    Toast.makeText(SigninActivity.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SigninActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSignInEmail.getText().toString().isEmpty()){
                    etSignInEmail.setError("Please Enter Email");
                } else {
                    mAuth.sendPasswordResetEmail(etSignInEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SigninActivity.this, "Password reset link send", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}