package com.example.contactapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private Context context;
    private ArrayList<ModelContact> contactList;
    private DBHelper dbHelper;

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item, parent, false);
        ContactViewHolder vh = new ContactViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ModelContact modelContact = contactList.get(position);

        // get data
        // нам необходимо только 3 поля
        String id = modelContact.getId();
        String image = modelContact.getImage();
        String name = modelContact.getName();

        // добавим остальные поля  /**/
        String phone = modelContact.getPhone();
        String email = modelContact.getEmail();
        String note = modelContact.getNote();
        String addedTime = modelContact.getAddedTime();
        String updatedTime = modelContact.getUpdateTime();

        // set data in view
        holder.contactName.setText(name);
        if(image.equals("null")){
            holder.contactImage.setImageResource(R.drawable.baseline_person_24);
//            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
        } else {
            holder.contactImage.setImageURI(Uri.parse(image));
            holder.contactImage.setClipToOutline(true);
        }

        holder.contactDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // handle item click and show contact details
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent to move to contactsDetails Activity with contact id as reference
                Intent intent = new Intent(context, ContactDetail.class);
                intent.putExtra("contactId", id);
                context.startActivity(intent);
            }
        });

        // handle editBtn click /**/
        holder.contactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                // create intent to move AddEditActivity to update data
                Intent intent = new Intent(context, AddEditContact.class);

                // pass thr value of
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("PHONE", phone);
                intent.putExtra("EMAIL", email);
                intent.putExtra("NOTE", note);
                intent.putExtra("ADDEDTIME", addedTime);
                intent.putExtra("UPDATETIME", updatedTime);
                intent.putExtra("IMAGE", image);

                // pass a boolean data dafine it is for edit purpose
                intent.putExtra("isEditMode", true);

                // start intent
                context.startActivity(intent);
            }
        });

        // handle deleteBtn click /**/
        holder.contactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                    dbHelper.deleteContact(id);
                    ((MainActivity)context).onResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView contactImage, contactDial, contactDelete, contactEdit;
        TextView contactName;
        RelativeLayout relativeLayout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contact_image);
            contactDial = itemView.findViewById(R.id.contact_number_dial);
            contactName = itemView.findViewById(R.id.contact_name);

            contactDelete = itemView.findViewById(R.id.right_view);
            contactEdit = itemView.findViewById(R.id.drag_item);

            relativeLayout = itemView.findViewById(R.id.mainLayout);


        }

    }
}
