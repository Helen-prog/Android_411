package com.example.fragmentmenuapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


public class ThirdPage extends Fragment {

   SearchView searchView;
   ListView listView;

   ArrayList<String> arrayList;

   ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_page, container, false);

        searchView = view.findViewById(R.id.search);
        listView = view.findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        arrayList.add("Архангельск");
        arrayList.add("Воронеж");
        arrayList.add("Астрахань");
        arrayList.add("Смоленск");
        arrayList.add("Владимир");
        arrayList.add("Волгоград");
        arrayList.add("Ставрополь");
        arrayList.add("Адлер");
        arrayList.add("Выборг");
        arrayList.add("Самара");

        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                Toast.makeText(getContext(), "!!!! " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                Toast.makeText(getContext(), "??? " + newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return view;
    }
}