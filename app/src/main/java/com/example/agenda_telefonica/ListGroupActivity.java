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
public class ListGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGroups;
    private DBGroupHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);

        dbHelper = new DBGroupHelper(this);
        recyclerViewGroups = findViewById(R.id.recyclerViewGroups);
        recyclerViewGroups.setLayoutManager(new LinearLayoutManager(this));

        Button newGroupEntryButton = findViewById(R.id.newGroupButton);
        newGroupEntryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListGroupActivity.this, GroupActivity.class);
            startActivity(intent);
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
        recyclerViewGroups.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
