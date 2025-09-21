package com.example.fiebaseapp.Signin;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.fiebaseapp.MainActivity;
import com.example.fiebaseapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ImageView ivUser;
    EditText etDisplayName;
    Button btnUpdatePassword, btnDeleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        ivUser = findViewById(R.id.iv_user);
        etDisplayName = findViewById(R.id.et_display_name);
        btnUpdatePassword = findViewById(R.id.btn_update_password);
        btnDeleteUser = findViewById(R.id.btn_delete_user);

        Glide.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(ivUser);
        etDisplayName.setText(mAuth.getCurrentUser().getDisplayName());

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_user();
            }
        });
    }

    private void delete_user() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_user);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, 2000);

        EditText etDelEmail = dialog.findViewById(R.id.et_del_email);
        EditText etDelPassword = dialog.findViewById(R.id.et_del_password);

        Button btnDelUser = dialog.findViewById(R.id.btn_del_user);

        btnDelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etDelEmail.getText().toString().isEmpty()){
                    etDelEmail.setError("Please Enter Email");
                } else if(etDelPassword.getText().toString().isEmpty()){
                    etDelPassword.setError("Please Enter Password");
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(etDelEmail.getText().toString(), etDelPassword.getText().toString());
                    mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task1) {
                                        if(task1.isSuccessful()){
                                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(ProfileActivity.this, task1.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ProfileActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    private void updatePassword() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.update_password);

        EditText etOldPassword = dialog.findViewById(R.id.et_old_password);
        EditText etNewPassword = dialog.findViewById(R.id.et_new_password);

        Button btnUpdatePassword = dialog.findViewById(R.id.btn_update_password);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etOldPassword.getText().toString().isEmpty()) {
                    etOldPassword.setError("Please Enter Old Password");
                } else if (etNewPassword.getText().toString().isEmpty()) {
                    etNewPassword.setError("Please New Password");
                } else if (etOldPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                    Toast.makeText(ProfileActivity.this, "New Password can not be same as existing Password", Toast.LENGTH_SHORT).show();
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail().toString(), etOldPassword.getText().toString());
                    mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().updatePassword(etNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task1) {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(ProfileActivity.this, task1.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ProfileActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
    }
}