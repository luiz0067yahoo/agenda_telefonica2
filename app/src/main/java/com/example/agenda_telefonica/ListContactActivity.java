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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class ListContactActivity extends AppCompatActivity {

    private RecyclerView recyclerViewContacts;
    private DBContactHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        dbHelper = new DBContactHelper(this);
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        Button newContactEntryButton = findViewById(R.id.newContactButton);
        newContactEntryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListContactActivity.this, ContactActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadContactData();
    }

    private void reloadContactData() {
        ArrayList<Contact> contacts = dbHelper.getAllContacts();
        ContactListAdapter adapter = new ContactListAdapter(this, contacts, dbHelper);
        recyclerViewContacts.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
