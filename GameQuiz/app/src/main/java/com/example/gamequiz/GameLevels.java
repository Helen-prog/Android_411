package com.example.gamequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameLevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_levels);

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int level = save.getInt("Level", 1);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameLevels.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // кнопка для перехода на 1 уровень
        TextView textView1 = findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level >= 1) {
                    Intent intent = new Intent(GameLevels.this, Level1.class);
                    startActivity(intent);
                }
            }
        });

        // кнопка для перехода на 2 уровень
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level >= 2) {
                    Intent intent = new Intent(GameLevels.this, Level2.class);
                    startActivity(intent);
                }
            }
        });

        // кнопка для перехода на 3 уровень
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level >= 3) {
                    Intent intent = new Intent(GameLevels.this, Level3.class);
                    startActivity(intent);
                }
            }
        });

        // кнопка для перехода на 4 уровень
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level >= 4) {
                    Intent intent = new Intent(GameLevels.this, Level4.class);
                    startActivity(intent);
                }
            }
        });

        final  int[] x = {
                R.id.textView1,
                R.id.textView2,
                R.id.textView3,
                R.id.textView4
        };

        for (int i = 0; i < level; i++) {
            TextView tv = findViewById(x[i]);
            tv.setText("" + (i + 1));
        }

        Button reset = findViewById(R.id.button_reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = save.edit();
                editor.putInt("Level", 1);
                editor.apply();
            }
        });
    }
}