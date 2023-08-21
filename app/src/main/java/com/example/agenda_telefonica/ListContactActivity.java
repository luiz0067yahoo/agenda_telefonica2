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
        ArrayList<Contact> contacts = dbHelper.getAllContacts();

        ContactListAdapter adapter = new ContactListAdapter(this, contacts, dbHelper);
        int count=0;
        for (Contact contact : contacts) {
            linearLayoutContacts.addView(adapter.getView(count, null, linearLayoutContacts));
            count++;
        }
    }
}