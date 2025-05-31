package com.example.fragmentmenuapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SecondPage extends Fragment {

    ImageButton beforeButton, nextButton;
    ImageSwitcher imageSwitcher;

    int index = 0;
    int[] mas = {R.drawable.inedible_5, R.drawable.inedible_6, R.drawable.inedible_7, R.drawable.inedible_8};

    EditText editText;
    Button add;
    RecyclerView recycleView;

    List<String> dataList = new ArrayList<>();
    DataAdapter adapter;

    EditText date, time;
    Calendar calendar;

    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_page, container, false);

        date = view.findViewById(R.id.dateInput);
        time = view.findViewById(R.id.timeInput);

//        date.setInputType(InputType.TYPE_NULL);
//        time.setInputType(InputType.TYPE_NULL);
        date.setFocusable(false);
        time.setFocusable(false);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time);
            }
        });

        editText = view.findViewById(R.id.editText);
        add = view.findViewById(R.id.add);
        recycleView = view.findViewById(R.id.recycleView);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new DataAdapter(dataList);
        recycleView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                dataList.add(data);
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        beforeButton = view.findViewById(R.id.before);
        nextButton = view.findViewById(R.id.next);
        imageSwitcher = view.findViewById(R.id.imageSwitcher);

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if(index < 0){
                    index = mas.length - 1;
                }
                imageSwitcher.setImageResource(mas[index]);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if(index == mas.length){
                    index = 0;
                }
                imageSwitcher.setImageResource(mas[index]);
            }
        });

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getActivity().getApplicationContext());

                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxWidth(250);
                imageView.setMaxHeight(350);
                return imageView;
            }
        });
        imageSwitcher.setImageResource(mas[index]);

        return view;
    }

    private void showTimeDialog(EditText time) {
        calendar = Calendar.getInstance();

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog(EditText date) {
        calendar = Calendar.getInstance();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}