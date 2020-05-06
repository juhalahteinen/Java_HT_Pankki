package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class UserSettings extends AppCompatActivity {
    ArrayList<String> credentialList = new ArrayList();
    EditText input;
    TextView fieldToChange;
    TextView fieldEditedInfo;


    // gets access to Bank class
    Bank bank = Bank.getInstance();
    int credentialNum;

    int aORn;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        Spinner spin = findViewById(R.id.userInfoMenu);
        input = findViewById(R.id.inputEdit);
        fieldToChange = findViewById(R.id.fieldToChange);
        fieldEditedInfo = findViewById(R.id.fieldEditedInfo);

        //gets info who has logged in
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");
        username = (String) getIntent().getSerializableExtra("username");

        credentialList.add("Name");
        credentialList.add("Address");
        credentialList.add("Country");

        //setting up spinner to select credential to edit
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, credentialList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {

                } else {
                    String credentialToEdit = credentialList.get(position);
                    credentialNum = position;
                    fieldToChange.setText("Insert new " + credentialToEdit);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void onSavePressed(View v) {
        switch (credentialNum) {
            // switch case to select actions to be done actions based on spinner position
            case 0:
                int i=-1;
                for (Users user : bank.userList) {
                    i++;
                    //searches active users account with for loop
                    if (username.equals(bank.userList.get(i).getUserName().trim())) {
                        bank.userList.get(i).setName(input.getText().toString().trim());
                        fieldEditedInfo.setText("Name changed to: "+input.getText()+".");
                    }
                }
                break;
            case 1:
                i = -1;
                for (Users user : bank.userList) {
                    i++;
                    if (username.equals(bank.userList.get(i).getUserName().trim())) {
                        bank.userList.get(i).setAddress(input.getText().toString().trim());
                        fieldEditedInfo.setText("Address changed to: "+input.getText()+".");
                    }
                }
                break;
            case 2:
                i = -1;
                for (Users user : bank.userList) {
                    i++;
                    if (username.equals(bank.userList.get(i).getUserName().trim())) {
                        bank.userList.get(i).setCountry(input.getText().toString().trim());
                        fieldEditedInfo.setText("Country changed to: "+input.getText()+".");
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //equals user being admin
        if (aORn == 1) {
            Intent intent = new Intent(UserSettings.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            System.out.println(username);
            System.out.println(aORn);
            startActivity(intent);

        //equals user being normal
        } else {
            Intent intent = new Intent(UserSettings.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}

