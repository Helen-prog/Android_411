package com.example.contactapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView contactRv;

//    ImageView phoneText = findViewById(R.id.contact_number_dial);
//
//    TextView phone_tv = findViewById(R.id.phone_tv);

    private DBHelper dbHelper;
    private AdapterContact adapterContact;

    private String sortByNewest = Constants.C_ADDED_TIME + " DESC";
    private String sortByOldest = Constants.C_ADDED_TIME + " ASC";

    private String sortByNameAsc = Constants.C_NAME + " ASC";
    private String sortByNameDesc = Constants.C_NAME + " DESC";

    private String currentSort = sortByNewest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        dbHelper = new DBHelper(this);

        fab = findViewById(R.id.fab);
        contactRv = findViewById(R.id.contactRv);

        contactRv.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditContact.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
            }
        });

//        phoneText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phoneNumber = phone_tv.getText().toString();
//                Intent intent = new Intent(Intent.ACTION_DIAL); //Открывает приложение звонков с уже подставленным номером.
//                intent.setData(Uri.parse("tel:" + phoneNumber));
//                startActivity(intent);
//            }
//        });

        loadData(currentSort);
    }

    private void loadData(String currentSort) {
        adapterContact = new AdapterContact(this, dbHelper.getAllData(currentSort));
        contactRv.setAdapter(adapterContact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(currentSort);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_top_menu, menu);

        MenuItem item = menu.findItem(R.id.searchContact);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContact(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContact(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchContact(String query) {
        adapterContact = new AdapterContact(this, dbHelper.getSearchContact(query));
        contactRv.setAdapter(adapterContact);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAllContact) {
            dbHelper.deleteAllContact();
            onResume();
        } else if (item.getItemId() == R.id.sortContact) {
            sortDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortDialog() {
        String[] options = {"Newest", "Oldest", "Name ASC", "Name Desc"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort By");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    loadData(sortByNewest);
                } else if (which == 1) {
                    loadData(sortByOldest);
                } else if (which == 2) {
                    loadData(sortByNameAsc);
                } else if (which == 3) {
                    loadData(sortByNameDesc);
                }
            }
        });
        builder.create().show();
    }
}


