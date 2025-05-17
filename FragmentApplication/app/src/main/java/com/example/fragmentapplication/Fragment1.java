package com.example.fragmentapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment1 extends Fragment {

    View view;
    EditText editText;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);

        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("key", editText.getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }
}