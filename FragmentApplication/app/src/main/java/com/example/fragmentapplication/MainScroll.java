package com.example.fragmentapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainScroll extends FragmentActivity {

    private static final int NUM_PAGES = 5;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scroll);

        viewPager2 = findViewById(R.id.pager);

        pageAdapter = new ScreenSliderAdapter(this);
        viewPager2.setAdapter(pageAdapter);
    }

    private class ScreenSliderAdapter extends FragmentStateAdapter {
        public ScreenSliderAdapter(MainScroll mainScroll) {
            super(mainScroll);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new FragmentOne();
                case 1:
                    return new FragmentTwo();
                case 2:
                    return new FragmentThree();
                case 3:
                    return new FragmentFour();
                case 4:
                    return new FragmentFive();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}