package com.example.agenda_telefonica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    long contactId =-1;
    ArrayList<Group> groupsList= new ArrayList<>();
    DBGroupHelper dbGroup=new DBGroupHelper(this);
    public int findGroupIndexById(ArrayList<Group> groupsList, long idGroup) {
        for (int i = 0; i < groupsList.size(); i++) {
            if (groupsList.get(i).getId() == idGroup) {
                return i; // Retorna o índice quando encontra o grupo com o id correspondente
            }
        }
        return -1; // Retorna -1 se não encontrar o grupo com o id correspondente
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBContactHelper DBContact = new DBContactHelper(this);
        setContentView(R.layout.activity_contact);
        EditText contactNameEditText = findViewById(R.id.contact_NameEditText);
        EditText contactPhoneEditText = findViewById(R.id.contactPhoneEditText);
        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        RadioButton maleRadioButton = findViewById(R.id.maleRadioButton);
        RadioButton femaleRadioButton = findViewById(R.id.femaleRadioButton);
        CheckBox favoriteCheckBox = findViewById(R.id.favoriteCheckBox);
        Spinner groupSpinner = findViewById(R.id.groupSpinner);
        Button clearButton = findViewById(R.id.clearButton);
        Button saveButton = findViewById(R.id.saveButton);
        Intent intent = getIntent();

        loadGroups(groupSpinner);

        contactNameEditText.setText("");
        contactPhoneEditText.setText("");
        genderRadioGroup.clearCheck();
        favoriteCheckBox.setChecked(false);
        groupSpinner.setSelection(-1);

        String contactName="";
        String phoneNumber="";
        String gender="";
        boolean favorite=false;
        long idGroup=-1;
        String nameGroup="";


        if (intent != null) {
            contactId = intent.getLongExtra("contactId", -1);
            contactName = intent.getStringExtra("contactName");
            phoneNumber = intent.getStringExtra("contactPhoneNumber");
            gender = intent.getStringExtra("contactGender");
            favorite = intent.getBooleanExtra("contactFavorite",false);
            idGroup = intent.getLongExtra("contactIdGroup", -1);
            nameGroup = intent.getStringExtra("contactNameGroup");
            contactNameEditText.setText(contactName);
            contactPhoneEditText.setText(phoneNumber);
            favoriteCheckBox.setChecked(favorite);
            if((gender!=null)&&(gender.length()>0)&&(gender.equals("Masculino"))){
                maleRadioButton.setChecked(true);
            }
            else if((gender!=null)&&(gender.length()>0)&&(gender.equals("Feminino"))){
                femaleRadioButton.setChecked(true);
            }
            groupSpinner.setSelection(findGroupIndexById(groupsList,idGroup));
        }

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactNameEditText.getText().clear();
                contactPhoneEditText.getText().clear();
                genderRadioGroup.clearCheck();
                favoriteCheckBox.setChecked(false);
                groupSpinner.setSelection(-1);
                showToast("Campos limpos");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void clean() {
                contactNameEditText.setText("");
                contactPhoneEditText.setText("");
                genderRadioGroup.clearCheck();
                favoriteCheckBox.setChecked(false);
                groupSpinner.setSelection(-1);
            }
            @Override
            public void onClick(View v) {
                String contactName = contactNameEditText.getText().toString();
                String phoneNumber = contactPhoneEditText.getText().toString();
                boolean favorite = favoriteCheckBox.isChecked();
                String gender = maleRadioButton.isChecked()?"Masculino":(femaleRadioButton.isChecked()?"Feminino":"");
                int indexGroup=groupSpinner.getSelectedItemPosition();
                long idGroupSelected=-1;
                String nameGroup="";
                if(indexGroup!=-1){
                    idGroupSelected=groupsList.get(indexGroup).getId();
                    nameGroup=groupsList.get(indexGroup).getName();
                }
                String erroMessage="";
                if(!(contactName!=null&&contactName.length()>0))
                {
                    erroMessage="Favor preencher o campo nome\n";
                } else if (!(phoneNumber!=null&&phoneNumber.length()>0)) {
                    erroMessage="Favor preencher o campo Telefone\n";
                } else if (!(gender!=null&&gender.length()>0)) {
                    erroMessage="Favor preencher o campo Sexo\n";
                } else if (indexGroup==-1) {
                    erroMessage="Favor selecione o campo Grupo\n";
                } else{
                    try {
                        if (contactId == -1) {
                            contactId = DBContact.insertContact(contactName, phoneNumber, gender, favorite, idGroupSelected);
                            if (contactId != -1) {
                                showToast("Contato  \"" + contactName + "\" Criado");
                                clean();
                            } else {
                                showToast("Erro ao criar o Contato.");
                            }
                        } else {
                            Contact contact = DBContact.updateContact(contactId, contactName, phoneNumber, gender, favorite, idGroupSelected, nameGroup);
                            if (contact != null) {
                                showToast("Grupo  \"" + contactName + "\" Atualizado");
                                clean();
                            } else {
                                showToast("Erro ao atualizar o grupo.");
                            }
                        }
                    } catch (Exception e) {
                        showToast("Erro: " + e.getMessage());
                    }
                    finish();
                }
                if (erroMessage!="")
                    showToast(erroMessage);
            }
        });
    }
    private void loadGroups(Spinner groupSpinner){

        groupsList= dbGroup.getAllGroups();
        String[] GroupNames = new String[groupsList.size()];
        for (int i = 0; i < groupsList.size(); i++) {
            Group group = groupsList.get(i);
            GroupNames[i] = group.getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GroupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}