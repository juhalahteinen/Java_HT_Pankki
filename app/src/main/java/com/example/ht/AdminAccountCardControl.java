package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdminAccountCardControl extends AppCompatActivity {

    String username;
    int aORn;

    int cardArray;
    int ListNumber;

    TextView pLimit;
    TextView wLimit;
    TextView cLimit;
    TextView cRegion;
    TextView accountType;
    TextView accountBalance;
    TextView optionalCLimit;

    Spinner spinAccount;
    Spinner spinCard;

    //gets access to Bank class
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_card_control);

        // gets information that user is admin
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        pLimit = (TextView) findViewById(R.id.payLView);
        wLimit = (TextView) findViewById(R.id.wLView);
        cLimit = (TextView) findViewById(R.id.cLView);
        cRegion = (TextView) findViewById(R.id.regView);
        accountType  = (TextView) findViewById(R.id.accTypeView);
        accountBalance = (TextView) findViewById(R.id.accBalanceView);
        optionalCLimit = (TextView) findViewById(R.id.optCLimit);

        spinAccount = findViewById(R.id.spinner4);
        spinCard = findViewById(R.id.spinner5);

        // makes accounts and cards available for spinners
        bank.accountSpinner(username, spinAccount, this);
        bank.cardSpinner(username, spinCard, this);

    }

    public void showAccountInfo(View v) {
        ListNumber = bank.accountSpinner(username, spinAccount, AdminAccountCardControl.this);
        accountType.setText(bank.accountList.get(ListNumber).getAccountType());
        accountBalance.setText(String.valueOf(bank.accountList.get(ListNumber).getBalance()));
    }

    public void showCardInfo(View v) {
        cardArray = bank.cardSpinner(username, spinCard, AdminAccountCardControl.this);

        String payLimit = String.valueOf(bank.accountList.get(cardArray).getPayLimit());
        String withdrawLimit = String.valueOf(bank.accountList.get(cardArray).getWithdrawLimit());
        String creditLimit = String.valueOf(bank.accountList.get(cardArray).getCreditLimit());
        String Region = String.valueOf(bank.accountList.get(cardArray).getRegion());

        pLimit.setText(payLimit);
        wLimit.setText(withdrawLimit);
        cRegion.setText(Region);

        if (bank.accountList.get(cardArray).getCreditLimit() > 0) {
            optionalCLimit.setText("Credit limit:" );
            cLimit.setText(creditLimit);
        }
    }

    public void removeAccount(View v) {
        bank.accountList.remove(ListNumber);
        accountBalance.setText("");
        accountType.setText("");
        bank.accountSpinner(username, spinAccount, AdminAccountCardControl.this);
        bank.cardSpinner(username, spinCard, AdminAccountCardControl.this);
        Toast.makeText(AdminAccountCardControl.this, "Account has been removed.", Toast.LENGTH_SHORT).show();


    }

    public void removeCard(View v) {
        bank.accountList.get(cardArray).setCard("no");
        bank.accountList.get(cardArray).setPayLimit(0);
        bank.accountList.get(cardArray).setWithdrawLimit(0);
        bank.accountList.get(cardArray).setRegion("");
        if (bank.accountList.get(cardArray).getCreditLimit() > 0) { // checks if a credit card
            bank.accountList.get(cardArray).setCreditLimit(0);
            cLimit.setText("");
        }
        pLimit.setText("");
        wLimit.setText("");
        cRegion.setText("");

        bank.cardSpinner(username, spinCard, AdminAccountCardControl.this);
        Toast.makeText(AdminAccountCardControl.this, "Card has been removed.", Toast.LENGTH_SHORT).show();
    }

    public void freezeAccount(View v) {
        bank.accountList.get(ListNumber).setFrozen("yes");
        Toast.makeText(AdminAccountCardControl.this, "Account has been set frozen.", Toast.LENGTH_SHORT).show();
    }


    public void cancelCard(View v) {
        bank.accountList.get(cardArray).setCancelled("cancelled");
        Toast.makeText(AdminAccountCardControl.this, "Card has been cancelled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminAccountCardControl.this, AdminActions.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }
}
