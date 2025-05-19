package com.example.fragmentapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

public class ActivityTab extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tabLayout = findViewById(R.id.tabs);

        FragmentManager fragmentManager = getSupportFragmentManager();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new FragmentOne()).commit();
                } else if (tab.getPosition() == 1) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new FragmentTwo()).commit();
                } else if (tab.getPosition() == 2) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new FragmentThree()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}