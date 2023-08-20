package com.example.agenda_telefonica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GroupActivity extends AppCompatActivity {
    long groupId =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        DBGroupHelper groups = new DBGroupHelper(this);
        EditText groupNameEditText = findViewById(R.id.groupNameEditText);
        Button clearButton = findViewById(R.id.clearButton);
        Button saveButton = findViewById(R.id.saveButton);
        Intent intent = getIntent();

        String groupName="";
        if (intent != null) {
            groupId = intent.getLongExtra("groupId", -1);
            groupName = intent.getStringExtra("groupName");
        }
        groupNameEditText.setText(groupName);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupNameEditText.getText().clear();
                showToast("Campos limpos");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String groupName = groupNameEditText.getText().toString();
                if(groupName!=null&&groupName.length()>0) {
                    try {
                        if (groupId == -1) {

                            groupNameEditText.getText().clear();
                            groupId = groups.insertGroup(groupName);
                            if (groupId != -1) {
                                showToast("Grupo  \"" + groupName + "\" Criado");
                            } else {
                                showToast("Erro ao criar o grupo.");
                            }
                        } else {
                            Group group = groups.updateGroup(groupId, groupName);
                            groupNameEditText.getText().clear();
                            if (group != null) {
                                showToast("Grupo  \"" + groupName + "\" Atualizado");
                            } else {
                                showToast("Erro ao atualizar o grupo.");
                            }
                        }
                    } catch (Exception e) {
                        showToast("Erro: " + e.getMessage());
                    }
                    finish();
                }
                else
                    showToast("Favor preencher o campo nome");
            }
        });

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}