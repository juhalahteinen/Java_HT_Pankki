package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    // gets access to Bank class
    Bank bank = Bank.getInstance();

    EditText accountNumber;
    EditText startDeposit;

    private Spinner spinner;
    String username;
    int aORn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        startDeposit = (EditText) findViewById(R.id.depositMoney);
        spinner = findViewById(R.id.createAccountSpinner);
        bank.accountTypeSpinner(spinner, this);

        //gets info who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");
    }

    public void saveAccount(View view) {
        String chosenAccountType = bank.accountTypeSpinner(spinner, CreateAccount.this);
        double deposit = Double.parseDouble(startDeposit.getText().toString());
        bank.accountList.add(new Account(username, chosenAccountType, accountNumber.getText().toString(), deposit, 0, null, "no", 0, "no", "no", 0));
        Toast.makeText(CreateAccount.this, "Account created successfully.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        // equals to user being admin
        if (aORn == 1) {
            Intent intent = new Intent(CreateAccount.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }

        // user being a normal user
        else {
            Intent intent = new Intent(CreateAccount.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}
