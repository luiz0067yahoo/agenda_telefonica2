package com.example.agenda_telefonica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button cadastrarContatoButton = findViewById(R.id.enterContactEntryButton);
        cadastrarContatoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir a ListContactActivity quando o botão for clicado
                Intent intent = new Intent(MenuActivity.this, ListContactActivity.class);
                startActivity(intent);
            }
        });

        Button enterGroupEntryButton = findViewById(R.id.enterGroupEntryButton);
        enterGroupEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir a GroupActivity quando o botão for clicado
                Intent intent = new Intent(MenuActivity.this, ListGroupActivity.class);
                startActivity(intent);
            }
        });
        DBGroupHelper groups = new DBGroupHelper(this);
        groups.onCreate();
        //groups.deleteAllGroups();
        groups.seedGroups();
        DBContactHelper contacts = new DBContactHelper(this);
        //contacts.onDestroy();
        contacts.onCreate();
        contacts.seedContacts();
    }
}
