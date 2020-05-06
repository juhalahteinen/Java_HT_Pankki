package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AdminMenu extends AppCompatActivity {

    // gets access to Bank class
    Bank bank = Bank.getInstance();

    Spinner spinner;
    String chosenUser;

    int aORn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        // gets information who has logged in
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        spinner = findViewById(R.id.chooseAcc);

        // spinner for choosing user to inspect
        List<String> uTotal = new ArrayList<>();

        uTotal.add(0, "");

        for (int i = 0; i < bank.userList.size(); i++) {
            uTotal.add(bank.userList.get(i).getUserName());
        }

        ArrayAdapter<String> totalUser = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, uTotal);
        totalUser.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(totalUser);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    chosenUser = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void continueToActions(View view) {
        Intent intent = new Intent(AdminMenu.this, AdminActions.class);
        intent.putExtra("username", chosenUser);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void adminLogOut(View view) {
        Intent intent = new Intent(AdminMenu.this, LogIn.class);
        startActivity(intent);
    }
}
