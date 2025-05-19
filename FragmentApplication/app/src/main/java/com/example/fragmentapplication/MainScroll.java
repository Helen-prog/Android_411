package com.example.fragmentapplication;

import android.os.Bundle;
import android.view.View;

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

        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
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

    private class ZoomOutPageTransformer implements ViewPager2.PageTransformer{

        private static final float MIN_SCALE = 0.05f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();
//            int pageHeight = page.getHeight();
            if (position < -1){
                page.setAlpha(0f);
            } else if (position <= 1){
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

                float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;

                if(position < 0){
                    page.setTranslationX(horizontalMargin / 2);
                } else {
                    page.setTranslationX(-horizontalMargin / 2);
                }
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_SCALE));
            } else {
                page.setAlpha(0f);
            }
        }
    }
}