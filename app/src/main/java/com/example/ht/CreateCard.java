package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateCard extends AppCompatActivity {

    //gets access to Bank class
    Bank bank = Bank.getInstance();

    private Spinner spinnerAccount;
    private Spinner spinnerRegion;

    TextView information;
    EditText withdrawLimit;
    EditText payLimit;
    EditText creditLimit;

    private int chosenAccount;
    private String chosenRegion;

    String username;
    int aORn;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        withdrawLimit = (EditText) findViewById(R.id.wLimit);
        payLimit = (EditText) findViewById(R.id.pLimit);
        creditLimit = (EditText) findViewById(R.id.cLimit);
        information = (TextView) findViewById(R.id.infoBox);
        spinnerAccount = findViewById(R.id.chooseAcc);
        spinnerRegion = findViewById(R.id.chooseReg);
        save = (Button) findViewById(R.id.saveCard);

        // gets info who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        //checks if there accounts to card be created on
        if (bank.accountList.size() != 0) {
            //spinner for accounts and using region
            bank.accountSpinner(username, spinnerAccount, this);
            bank.regionSpinner(spinnerRegion, this);
        } else {
            information.setText("No accounts exist, please return to create new.");
        }

        //to show account type
        withdrawLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setText("Account type for chosen account: " + bank.accountList.get(chosenAccount).getAccountType()); // in case of user doesn't remember account type
            }
        });
    }


    public void saveCard(View v) {
        chosenAccount = bank.accountSpinner(username, spinnerAccount, CreateCard.this);

        // checks if card already exists
        if (bank.accountList.get(chosenAccount).getCard().equals("yes")) {
            information.setText("A card already exists. Please choose other account.");

        // if debit account
        } else if (bank.accountList.get(chosenAccount).getAccountType().equals("Debit account")) {
            chosenRegion = bank.regionSpinner(spinnerRegion, CreateCard.this);
            double wLimit = Double.parseDouble(withdrawLimit.getText().toString());
            double pLimit = Double.parseDouble(payLimit.getText().toString());

            bank.accountList.get(chosenAccount).setCard("yes");
            bank.accountList.get(chosenAccount).setRegion(chosenRegion);
            bank.accountList.get(chosenAccount).setWithdrawLimit(wLimit);
            bank.accountList.get(chosenAccount).setPayLimit(pLimit);
            information.setText("Card created successfully");

            // if credit account
        } else {
            chosenRegion = bank.regionSpinner(spinnerRegion, CreateCard.this);
            double wLimit = Double.parseDouble(withdrawLimit.getText().toString());
            double pLimit = Double.parseDouble(payLimit.getText().toString());
            double cLimit = Double.parseDouble(creditLimit.getText().toString());

            bank.accountList.get(chosenAccount).setCard("yes");
            bank.accountList.get(chosenAccount).setRegion(chosenRegion);
            bank.accountList.get(chosenAccount).setWithdrawLimit(wLimit);
            bank.accountList.get(chosenAccount).setPayLimit(pLimit);
            bank.accountList.get(chosenAccount).setCreditLimit(cLimit);
            information.setText("Card created successfully");
        }
    }

    @Override
    public void onBackPressed() {
        //equals user being admin
        if (aORn == 1) {
            Intent intent = new Intent(CreateCard.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }

        //equals user being normal user
        else {
            Intent intent = new Intent(CreateCard.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}
