package com.example.second;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ImageView image = findViewById(R.id.boy_image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  ((AnimationDrawable) image.getDrawable()).start();
//                AnimationDrawable animationDrawable = (AnimationDrawable) image.getDrawable();
//                animationDrawable.start();

//                Animation out = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.anim_alpha);

//                Animation out = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_alpha);
//                image.startAnimation(out);

                Animation translate = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.anim_translate);
                image.startAnimation(translate);
            }
        });

        ImageView myImageView = findViewById(R.id.boy_image_0);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(myImageView, "rotation", 90);
        ObjectAnimator trY = ObjectAnimator.ofFloat(myImageView, "translationY", 300);

        AnimatorSet set = new AnimatorSet();
        set.play(rotation).with(trY);
        set.setDuration(1000);
        set.setStartDelay(1000);
        set.start();


        Button buttonRotateCenter = findViewById(R.id.rotateCenter);
        Button buttonRotateCorner = findViewById(R.id.rotateCorner);

        Animation animationRotateCenter = AnimationUtils.loadAnimation(this, R.anim.rotate_center);
        Animation animationRotateCorner = AnimationUtils.loadAnimation(this, R.anim.rotate_corner);

        buttonRotateCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myImageView.startAnimation(animationRotateCenter);
            }
        });

        buttonRotateCorner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myImageView.startAnimation(animationRotateCorner);
            }
        });

        Animation scale = AnimationUtils.loadAnimation(this, R.anim.shrink);
        image.startAnimation(scale);




    }
}