package com.example.filemangerprojectaplication.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filemangerprojectaplication.FileAdapter;
import com.example.filemangerprojectaplication.FileOpener;
import com.example.filemangerprojectaplication.OnFileSelectedListener;
import com.example.filemangerprojectaplication.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

public class InternalFragment extends Fragment implements OnFileSelectedListener {

    private FileAdapter fileAdapter;

    private RecyclerView recyclerView;
    private List<File> fileList;
    private ImageView imgBack;
    private TextView tvPathHolder;

    File storage;
    String data;
    View view;

    String[] items = {"Details", "Rename", "Delete"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_internal, container, false);

        imgBack = view.findViewById(R.id.img_back);
        tvPathHolder = view.findViewById(R.id.tv_path_holder);

        // Получаем доступ к внутренней SD-карте
        String internalStorage = System.getenv("EXTERNAL_STORAGE");
        storage = new File(internalStorage);

        if (getArguments() != null) {
            data = getArguments().getString("path");
            storage = new File(data);
        }

        tvPathHolder.setText(storage.getAbsolutePath());
        runtimePermission();

        return view;
    }

    private void runtimePermission() {
        // Разрешение до Android 10 (API 29)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                displayFiles();
            }
        }
        // Разрешения для Android 11 (API 30) и выше
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(uri);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getActivity().getPackageName())));
                    getActivity().startActivityIfNeeded(intent, 101);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    getActivity().startActivityIfNeeded(intent, 101);
                }
            }
            if (Environment.isExternalStorageManager()) {
                displayFiles();
            }
        }
    }

    public ArrayList<File> findFiles(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.add(singleFile);
            }
        }

        for (File singleFile : files) {
            if (singleFile.getName().toLowerCase().endsWith(".jpeg") ||
                    singleFile.getName().toLowerCase().endsWith(".jpg") ||
                    singleFile.getName().toLowerCase().endsWith(".png") ||
                    singleFile.getName().toLowerCase().endsWith(".mp3") ||
                    singleFile.getName().toLowerCase().endsWith(".wav") ||
                    singleFile.getName().toLowerCase().endsWith(".mp4") ||
                    singleFile.getName().toLowerCase().endsWith(".pdf") ||
                    singleFile.getName().toLowerCase().endsWith(".doc") ||
                    singleFile.getName().toLowerCase().endsWith(".apk")) {
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }

    private void displayFiles() {
        recyclerView = view.findViewById(R.id.recycler_internal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fileList = new ArrayList<>();
        fileList.addAll(findFiles(storage));
//        System.out.println("!!!!!!!!!!!" + fileList);

        fileAdapter = new FileAdapter(getContext(), fileList, this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    public void onFileClicked(File file) {
        if (file.isDirectory()) {
            Bundle bundle = new Bundle();
            bundle.putString("path", file.getAbsolutePath());
            InternalFragment internalFragment = new InternalFragment();
            internalFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, internalFragment).addToBackStack(null).commit();
        } else {
            FileOpener.openFile(getContext(), file);
        }
    }

    @Override
    public void onFileLongClicked(File file, int position) {
        final Dialog optionDialog = new Dialog(getContext());
        optionDialog.setContentView(R.layout.option_dialog);
        optionDialog.setTitle("Select Options.");
        ListView options = optionDialog.findViewById(R.id.list);

        CustomAdapter customAdapter = new CustomAdapter();
        options.setAdapter(customAdapter);
        optionDialog.show();

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch (selectedItem) {
                    case "Details":
                        AlertDialog.Builder detailDialog = new AlertDialog.Builder(getContext());
                        detailDialog.setTitle("Details:");
                        final TextView details = new TextView(getContext());
                        detailDialog.setView(details);
                        Date lastModified = new Date(file.lastModified());
                        SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String formattedDate = formatted.format(lastModified);

//                        details.setText(String.format(
//                                "File Name: " + file.getName() + "\n" +
//                                        "Size: " + Formatter.formatShortFileSize(getContext(), file.length()) + "\n"
//                                        + "Path: " + file.getAbsolutePath() + "\n" +
//                                        "Last Modified: " + formattedDate));

                        details.setText(String.format(
                                "File Name: %s\nSize: %s\nPath: %s\nLast Modified: %s", file.getName(), Formatter.formatShortFileSize(getContext(), file.length()), file.getAbsolutePath(), formattedDate)
                        );

                        details.setPadding(70, 10, 10, 10);

                        detailDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                optionDialog.cancel();
//                                optionDialog.closeOptionsMenu();
                            }
                        });

                        AlertDialog alertDialogDetails = detailDialog.create();
                        alertDialogDetails.show();
                        break;

                    case "Rename":
                        AlertDialog.Builder renameDialog = new AlertDialog.Builder(getContext());
                        renameDialog.setTitle("Rename file:");
                        final EditText name = new EditText(getContext());
                        renameDialog.setView(name);

                        renameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = name.getEditableText().toString();

                                File current = new File(file.getAbsolutePath());
                                File destination;
                                if (!file.isDirectory()) {
                                    String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));

                                    destination = new File(file.getAbsolutePath().replace(file.getName(), newName) + extension);
                                } else {
                                    destination = new File(file.getAbsolutePath().replace(file.getName(), newName));
                                }
                                if (current.renameTo(destination)) {
                                    fileList.set(position, destination);
                                    fileAdapter.notifyItemChanged(position);
                                    Toast.makeText(getContext(), "Renamed!", Toast.LENGTH_SHORT).show();
                                    displayFiles();
                                } else {
                                    Toast.makeText(getContext(), "Couldn't Rename!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        renameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                optionDialog.cancel();
                            }
                        });

                        AlertDialog alertDialogRename = renameDialog.create();
                        alertDialogRename.show();
                        break;
                    case "Delete":
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
                        deleteDialog.setTitle("Delete " + file.getName() + "?");

                        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                file.delete();
                                displayFiles();
//                                fileList.remove(position);
//                                fileList.clear();
//                                fileList.addAll(findFiles(storage));
//                                fileAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Deleted file: " + file.getName(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                optionDialog.cancel();
                            }
                        });

                        AlertDialog alertDialogDelete = deleteDialog.create();
                        alertDialogDelete.show();
                        break;
                }
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View myView = getLayoutInflater().inflate(R.layout.option_layout, null);
            TextView txtOptions = myView.findViewById(R.id.txt_option);
            ImageView imgOptions = myView.findViewById(R.id.img_option);

            txtOptions.setText(items[position]);
            if (items[position].equals("Details")) {
                imgOptions.setImageResource(R.drawable.baseline_info_outline_24);
            } else if (items[position].equals("Rename")) {
                imgOptions.setImageResource(R.drawable.baseline_drive_file_rename_outline_24);
            } else if (items[position].equals("Delete")) {
                imgOptions.setImageResource(R.drawable.baseline_delete_forever_24);
            }

            return myView;
        }
    }
}