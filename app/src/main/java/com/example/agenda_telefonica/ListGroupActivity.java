package com.example.agenda_telefonica;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class ListGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerViewContacts;
    private DBGroupHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);

        dbHelper = new DBGroupHelper(this);
        recyclerViewContacts = findViewById(R.id.recyclerViewGroups);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        Button newGroupEntryButton = findViewById(R.id.newGroupButton);
        newGroupEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListGroupActivity.this, GroupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadGroupData();
    }

    private void reloadGroupData() {
        ArrayList<Group> groups = dbHelper.getAllGroups();
        GroupListAdapter adapter = new GroupListAdapter(this, groups, dbHelper);
        recyclerViewContacts.setAdapter(adapter);
    }

}