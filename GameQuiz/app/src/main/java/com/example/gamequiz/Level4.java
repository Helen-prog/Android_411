package com.example.gamequiz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;
import java.util.Random;

public class Level4 extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    public int numLeft; // переменная для левой картинки + текст
    public int numRight; // переменная для правой картинки + текст
    Array array = new Array();
    Random random = new Random();
    public int count = 0; // счетчик правильных ответов

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        // устанавливаем номер уровня
        TextView textLevels = findViewById(R.id.textView);
        textLevels.setText(R.string.level_4);

        textLevels.setTextColor(R.color.black_middle);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ImageView imgLeft = findViewById(R.id.img_left);
        final ImageView imgRight = findViewById(R.id.img_right);
        // скругление углов
        imgLeft.setClipToOutline(true);
        imgRight.setClipToOutline(true);

        final TextView textLeft = findViewById(R.id.text_left);
        textLeft.setTextColor(R.color.black_middle);
        final TextView textRight = findViewById(R.id.text_right);
        textRight.setTextColor(R.color.black_middle);

        ConstraintLayout background = findViewById(R.id.main);
        background.setBackgroundResource(R.drawable.level_4);

        // вызов диалогового окна в начале игры
        dialog = new Dialog(this);  // создаем новое диалоговое окно
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // скрываем заголовок
        dialog.setContentView(R.layout.preview_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // прозрачный фон диалогового окна
        dialog.setCancelable(false);  // окно нельзя закрыть кликом за пределами окна

        // устанавливаем картинку в диалоговое окно
        ImageView previewImg = dialog.findViewById(R.id.preview_img);
        previewImg.setImageResource(R.drawable.preview_img_4);

        // устанавливаем фон диалогового окна
        LinearLayout dialogFon = dialog.findViewById(R.id.dialog_fon);
        dialogFon.setBackgroundResource(R.drawable.preview_background_4);

        // устанавливаем описание задания
        TextView textDescription = dialog.findViewById(R.id.text_description);
        textDescription.setText(R.string.level_four);

        // кнопка Назад
        TextView btnClose = dialog.findViewById(R.id.button_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level4.this, GameLevels.class);
                startActivity(intent);
                dialog.dismiss();  // закрытие диалогового окна
            }
        });

        // кнопка Продолжить
        Button btnContinue = dialog.findViewById(R.id.button_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();  // закрытие диалогового окна
            }
        });

        dialog.show();  // показать диалоговое окно

        // ---------------------------------------------------------
        // Вызов диалогового окна в конце игры
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);  // скрываем заголовок
        dialogEnd.setContentView(R.layout.dialog_end);  // путь к диалоговому окну
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // фон прозрачный
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT); // чтобы диалоговое окно расширялось на всю ширину экрана
        dialogEnd.setCancelable(false);  // нельзя закрыть окно кликом за пределами окна

        LinearLayout dialogFonEnd = dialogEnd.findViewById(R.id.dialog_fon);
        dialogFonEnd.setBackgroundResource(R.drawable.preview_background_4);

        // интересный факт
        TextView textDescriptionEnd = dialogEnd.findViewById(R.id.text_description_end);
        textDescriptionEnd.setText(R.string.level_four_end);

        TextView btnClose2 = dialogEnd.findViewById(R.id.button_close);
        btnClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // вернемся к выбору уровня
                Intent intent = new Intent(Level4.this, GameLevels.class);
                startActivity(intent);
                dialogEnd.dismiss();
            }
        });

        Button buttonContinue2 = dialogEnd.findViewById(R.id.button_continue);
        buttonContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level4.this, GameLevels.class);
                startActivity(intent);
                dialogEnd.dismiss();
            }
        });

        // ---------------------------------------------------------

        // Кнопка Назад из окна с уровнем
        Button btnBack = findViewById(R.id.button_back_level1);

        btnBack.setBackgroundResource(R.drawable.button_stroke_black_opacity_press_white);
        btnBack.setTextColor(R.color.black_middle);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level4.this, GameLevels.class);
                startActivity(intent);
            }
        });

        final Animation animation = AnimationUtils.loadAnimation(Level4.this, R.anim.alpha);

        // генерировали случайное значение для левой картинки
        numLeft = random.nextInt(20);
        imgLeft.setImageResource(array.images4[numLeft]);
        textLeft.setText(array.text4[numLeft]);  // достаем из массива текст

        // генерировали случайное значение для правой картинки

        do {
            numRight = random.nextInt(20);
        } while (array.choice[numLeft] == array.choice[numRight]);

        imgRight.setImageResource(array.images4[numRight]);
        textRight.setText(array.text4[numRight]);  // достаем из массива текст

        // массив для прогресса игры
        final int[] progress = {R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10, R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15, R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20};

        // обрабатываем нажатие на левую картинку
        imgLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // условия касания картинки
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imgRight.setEnabled(false);  // блокируем правую картинку
                    if (array.choice[numLeft] > array.choice[numRight]) {
                        imgLeft.setImageResource(R.drawable.img_true);
                    } else {
                        imgLeft.setImageResource(R.drawable.img_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {  // если отпустили палец
                    if (array.choice[numLeft] > array.choice[numRight]) {
                        if (count < 20) {
                            count++;
                        }

                        // закрашиваем прогресс серым цветом
                        for (int j : progress) {
                            TextView tv = findViewById(j);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        // определяем правильные ответы и закрашивает зеленым
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else {  // если левая картинка меньше
                        if (count > 0) {
                            if (count == 1) {
                                count = 0;
                            } else {
                                count -= 2;
                            }
                            // закрашиваем прогресс серым цветом
                            for (int i = 0; i < progress.length - 1; i++) {
                                TextView tv = findViewById(progress[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            // определяем правильные ответы и закрашиваем зеленым цветом
                            for (int i = 0; i < count; i++) {
                                TextView tv = findViewById(progress[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                    }
                    if (count == 20) {  // выход из уровня
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(20);
                        imgLeft.setImageResource(array.images4[numLeft]);  // достаем из массива картинку
                        imgLeft.startAnimation(animation);  // запускаем анимацию для левой картинки
                        textLeft.setText(array.text4[numLeft]);  // достаем из массива текст

                        do {
                            numRight = random.nextInt(20);
                        } while (array.choice[numLeft] == array.choice[numRight]);
                        imgRight.setImageResource(array.images4[numRight]);  // достаем из массива картинку
                        imgRight.startAnimation(animation);  // запускаем анимацию для правой картинки
                        textRight.setText(array.text4[numRight]);  // достаем из массива текст
                        imgRight.setEnabled(true);  // включаем обратно правую картинку
                    }
                }
                return true;
            }
        });

        // обрабатываем нажатие на правую картинку
        imgRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // условия касания картинки
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imgLeft.setEnabled(false);  // блокируем левую картинку
                    if (array.choice[numLeft] < array.choice[numRight]) {
                        imgRight.setImageResource(R.drawable.img_true);
                    } else {
                        imgRight.setImageResource(R.drawable.img_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {  // если отпустили палец
                    if (array.choice[numLeft] < array.choice[numRight]) {
                        if (count < 20) {
                            count++;
                        }

                        // закрашиваем прогресс серым цветом
                        for (int j : progress) {
                            TextView tv = findViewById(j);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        // определяем правильные ответы и закрашивает зеленым
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else {  // если левая картинка меньше
                        if (count > 0) {
                            if (count == 1) {
                                count = 0;
                            } else {
                                count -= 2;
                            }
                            // закрашиваем прогресс серым цветом
                            for (int i = 0; i < progress.length - 1; i++) {
                                TextView tv = findViewById(progress[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            // определяем правильные ответы и закрашиваем зеленым цветом
                            for (int i = 0; i < count; i++) {
                                TextView tv = findViewById(progress[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                    }
                    if (count == 20) {  // выход из уровня
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(20);
                        imgLeft.setImageResource(array.images4[numLeft]);  // достаем из массива картинку
                        imgLeft.startAnimation(animation);  // запускаем анимацию для левой картинки
                        textLeft.setText(array.text4[numLeft]);  // достаем из массива текст

                        do {
                            numRight = random.nextInt(20);
                        } while (array.choice[numLeft] == array.choice[numRight]);
                        imgRight.setImageResource(array.images4[numRight]);  // достаем из массива картинку
                        imgRight.startAnimation(animation);  // запускаем анимацию для правой картинки
                        textRight.setText(array.text4[numRight]);  // достаем из массива текст
                        imgLeft.setEnabled(true);  // включаем обратно правую картинку
                    }

                }

                return true;
            }
        });
    }
}