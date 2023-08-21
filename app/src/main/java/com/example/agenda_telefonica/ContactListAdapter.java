package com.example.agenda_telefonica;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contact> contacts;
    private DBContactHelper dbHelper;

    public ContactListAdapter(Context context, ArrayList<Contact> contacts, DBContactHelper dbHelper) {
        this.context = context;
        this.contacts = contacts;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contacts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_contact, parent, false);

        TextView contactNameTextView = view.findViewById(R.id.cardContactName);
        TextView contactPhoneTextView = view.findViewById(R.id.cardContactPhone);
        TextView contactGenderTextView = view.findViewById(R.id.cardContactGender);
        TextView contactGroupTextView = view.findViewById(R.id.cardContactGroup);
        ImageView contactFavoriteImageView = view.findViewById(R.id.cardContactFavorite);
        Button editButton = view.findViewById(R.id.editButtonContact);
        Button deleteButton = view.findViewById(R.id.deleteButtonContact);

        Contact contact = contacts.get(position);

        // Configure os elementos da view com os dados do contato
        contactNameTextView.setText("NOME: "+contact.getName());
        contactPhoneTextView.setText("TELEFONE: "+contact.getPhone());
        contactGenderTextView.setText("SEXO: "+contact.getGender());
        contactGroupTextView.setText("GRUPO: "+contact.getNameGroup());
        if(contact.isFavorite()) {
            contactFavoriteImageView.setImageResource(R.drawable.ic_star);
        }
        else{
            contactFavoriteImageView.setImageResource(R.drawable.ic_star_outline);
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContactActivity.class);
            intent.putExtra("contactId", contact.getId()); // id do grupo
            intent.putExtra("contactName", contact.getName()); // nome do grupo
            intent.putExtra("contactPhoneNumber", contact.getPhone()); // nome do grupo
            intent.putExtra("contactGender", contact.getGender()); // nome do grupo
            intent.putExtra("contactFavorite", contact.isFavorite()); // nome do grupo
            intent.putExtra("contactIdGroup", contact.getIdGroup()); // nome do grupo
            intent.putExtra("contactNameGroup", contact.getNameGroup()); // nome do grupo
            context.startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteContact(contact.getId());
            showToast(contact.getName()+" excluido ");
        });

        return view;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
