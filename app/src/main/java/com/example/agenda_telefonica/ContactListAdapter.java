package com.example.agenda_telefonica;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;
    private DBContactHelper dbHelper;
    private Context context;

    public ContactListAdapter(Context context, ArrayList<Contact> contacts, DBContactHelper dbHelper) {
        this.context = context;
        this.contacts = contacts;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        holder.contactNameTextView.setText("NOME: "+contact.getName());
        holder.contactPhoneTextView.setText("TELEFONE: "+contact.getPhone());
        holder.contactGenderTextView.setText("SEXO: "+contact.getGender());
        holder.contactGroupTextView.setText("GRUPO: "+contact.getNameGroup());
       if(contact.isFavorite()) {
            holder.contactFavoriteImageView.setImageResource(R.drawable.ic_star);
        }
        else{
            holder.contactFavoriteImageView.setImageResource(R.drawable.ic_star_outline);
        }
        holder.editButton.setOnClickListener(v -> {
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

        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteContact(contact.getId());
            showToast(contact.getName() + " excluído");
            contacts.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactNameTextView;
        TextView contactPhoneTextView;
        TextView contactGenderTextView;
        TextView contactGroupTextView ;
        ImageView contactFavoriteImageView;
        // Outros elementos da view holder

        Button editButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.cardContactName);
            contactPhoneTextView = itemView.findViewById(R.id.cardContactPhone);
            contactGenderTextView = itemView.findViewById(R.id.cardContactGender);
            contactGroupTextView  = itemView.findViewById(R.id.cardContactGroup);
            contactFavoriteImageView = itemView.findViewById(R.id.cardContactFavorite);

            editButton = itemView.findViewById(R.id.editButtonContact);
            deleteButton = itemView.findViewById(R.id.deleteButtonContact);
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}