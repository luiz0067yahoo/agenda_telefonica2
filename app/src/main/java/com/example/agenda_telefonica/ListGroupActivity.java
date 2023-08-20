package com.example.agenda_telefonica;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;



public class ListGroupActivity extends AppCompatActivity {

    private LinearLayout linearLayoutGroups;
    private DBGroupHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBGroupHelper(this);
        setContentView(R.layout.activity_list_group);
        linearLayoutGroups = findViewById(R.id.LinearLayoutGroups);
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
        linearLayoutGroups.removeAllViews();
        ArrayList<Group>  groups = dbHelper.getAllGroups();
        for (Group group : groups) {
            addGroupCard(group.getId(),group.getName());
        }
    }
    private void addGroupCard(long id,String name) {

        View cardView = LayoutInflater.from(this).inflate(R.layout.activity_item_group, null);
        TextView cardGroupName = cardView.findViewById(R.id.cardGroupName);
        Button editButton = cardView.findViewById(R.id.editButtonGroup);
        Button deleteButton = cardView.findViewById(R.id.deleteButtonGroup);

        cardGroupName.setText(name);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListGroupActivity.this, GroupActivity.class);
                intent.putExtra("groupId", id); // id do grupo
                intent.putExtra("groupName", name); // nome do grupo
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteGroup(id);
                showToast(name+" excluido ");
                reloadGroupData();
            }
        });





        linearLayoutGroups.addView(cardView);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}