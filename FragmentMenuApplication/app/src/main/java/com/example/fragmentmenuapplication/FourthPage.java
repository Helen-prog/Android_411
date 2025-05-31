package com.example.fragmentmenuapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

public class FourthPage extends Fragment {

   TextView textView, textViewLang;
   NumberPicker numberPicker, numberPickerLang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_page, container, false);

        textView = view.findViewById(R.id.textViewNumberPicker);
        numberPicker = view.findViewById(R.id.numberPicker);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setValue(5);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textView.setText("Selected: " + newVal);
            }
        });

        textViewLang = view.findViewById(R.id.textViewCountryPicker);
        numberPickerLang = view.findViewById(R.id.cityPicker);

        Language.initLanguages();

        numberPickerLang.setMaxValue(Language.getLanguageArrayList().size() - 1);
        numberPickerLang.setMinValue(0);
        numberPickerLang.setDisplayedValues(Language.languageNames());

        numberPickerLang.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textViewLang.setText("Selected: " + Language.getLanguageArrayList().get(newVal).getName());
            }
        });

        return view;
    }
}