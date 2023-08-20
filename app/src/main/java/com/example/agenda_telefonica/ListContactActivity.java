package com.example.agenda_telefonica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListContactActivity extends AppCompatActivity {

    private LinearLayout linearLayoutContacts;
    private DBContactHelper dbHelper;
    ArrayList<Group> groupsList= new ArrayList<>();
    DBGroupHelper dbGroup=new DBGroupHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBContactHelper DBContact = new DBContactHelper(this);
        groupsList= dbGroup.getAllGroups();
        dbHelper = new DBContactHelper(this);
        setContentView(R.layout.activity_list_contact);
        linearLayoutContacts = findViewById(R.id.LinearLayoutContats);
        Button newContactEntryButton = findViewById(R.id.newContactButton);
        newContactEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContactActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        reloadContactData();
    }

    private void reloadContactData() {
        linearLayoutContacts.removeAllViews();
        ArrayList<Contact>  contacts = new ArrayList<>();
         contacts = dbHelper.getAllContacts();
        for (Contact contact : contacts) {
            addContactCard(contact.getId(),contact.getName(),contact.getPhone(),contact.getGender(),contact.isFavorite(),contact.getIdGroup(),contact.getNameGroup());
        }
    }
    private void addContactCard(long id,String contactName,String phoneNumber,String gender,boolean favorite,long  idGroup,String  nameGroup) {

        View contactCardView = LayoutInflater.from(this).inflate(R.layout.activity_item_contact, null);
        TextView contactNameTextView = contactCardView.findViewById(R.id.cardContactName);
        TextView contactPhoneTextView = contactCardView.findViewById(R.id.cardContactPhone);
        TextView contactGenderTextView = contactCardView.findViewById(R.id.cardContactGender);
        TextView contactGroupTextView = contactCardView.findViewById(R.id.cardContactGroup);
        ImageView contactFavoriteImageView = contactCardView.findViewById(R.id.cardContactFavorite);
        Button editButton = contactCardView.findViewById(R.id.editButtonContact);
        Button deleteButton = contactCardView.findViewById(R.id.deleteButtonContact);
        contactNameTextView.setText("NOME: "+contactName);
        contactPhoneTextView.setText("TELEFONE: "+phoneNumber);
        contactGenderTextView.setText("SEXO: "+gender);
        contactGroupTextView.setText("GRUPO: "+nameGroup);
        if(favorite) {
            contactFavoriteImageView.setImageResource(R.drawable.ic_star);
        }
        else{
            contactFavoriteImageView.setImageResource(R.drawable.ic_star_outline);
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContactActivity.this, ContactActivity.class);
                intent.putExtra("contactId", id); // id do grupo
                intent.putExtra("contactName", contactName); // nome do grupo
                intent.putExtra("contactPhoneNumber", phoneNumber); // nome do grupo
                intent.putExtra("contactGender", gender); // nome do grupo
                intent.putExtra("contactFavorite", favorite); // nome do grupo
                intent.putExtra("contactIdGroup", idGroup); // nome do grupo
                intent.putExtra("contactNameGroup", nameGroup); // nome do grupo
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteContact(id);
                showToast(contactName+" excluido ");
                reloadContactData();
            }
        });

        linearLayoutContacts.addView(contactCardView);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}