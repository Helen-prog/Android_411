package com.example.fragmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    TextView data;

    Button firstFragmentBtn, secondFragmentBtn;
    Fragment1 firstFragment = new Fragment1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFragmentBtn = findViewById(R.id.fragment1Btn);
        secondFragmentBtn = findViewById(R.id.fragment2Btn);

        replaceFragment(firstFragment);

        firstFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(firstFragment);
            }
        });

        secondFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment2 secondFragment = new Fragment2();
                replaceFragment(secondFragment);
            }
        });

        data = findViewById(R.id.data);

        Intent intent = getIntent();
        String getData = intent.getStringExtra("key");
        data.setText(getData);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}