package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardWithdrawPay extends AppCompatActivity {

    //gets access to Bank class
    Bank bank = Bank.getInstance();

    String username;
    int aORn;

    TextView payLimitView;
    TextView wdLimitView;
    TextView cLimitText;
    TextView cLimitView;
    EditText withdrawAmount;
    EditText paymentAmount;
    EditText useRegion;
    int cardChosen;
    Spinner spinCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_withdraw_pay);
        payLimitView = findViewById(R.id.payLimitView);
        wdLimitView = findViewById(R.id.wdLimitView);
        cLimitText = findViewById(R.id.cLimitText);
        cLimitView = findViewById(R.id.cLimitView);
        withdrawAmount = findViewById(R.id.withdrawAmount);
        paymentAmount = findViewById(R.id.paymentAmount);
        spinCard = findViewById(R.id.spinner2);
        useRegion = findViewById(R.id.usingRegion);

        // gets info who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        // checks if card(s) exist
        int check = 0;
        for (int i = 0; i < bank.accountList.size(); i++) {
            if (bank.accountList.get(i).getCard().equals("yes")) {
                check += 1;
            }
        }

        if (check == 0) {
            Toast.makeText(CardWithdrawPay.this, "No bank cards exist. Please return to create new.", Toast.LENGTH_SHORT).show();
        } else {
            //spinner for choosing card
            bank.cardSpinner(username, spinCard, this);
        }

        // shows old info about card
        useRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardChosen = bank.cardSpinner(username, spinCard, CardWithdrawPay.this);
                payLimitView.setText(String.valueOf(bank.accountList.get(cardChosen).getPayLimit()));
                wdLimitView.setText(String.valueOf(bank.accountList.get(cardChosen).getWithdrawLimit()));
                if (bank.accountList.get(cardChosen).getAccountType().equals("Credit account")) {
                    cLimitText.setText("Credit limit: ");
                    cLimitView.setText(String.valueOf(bank.accountList.get(cardChosen).getCreditLimit()));
                } else if (bank.accountList.get(cardChosen).getAccountType().equals("Debit account")) {
                    cLimitText.setText("Credit limit: ");
                    cLimitView.setText("-");
                }
            }
        });
    }

    // checks if card is not cancelled and can be used in written area/region
    // withdraws give amount and adds transaction if terms above are ok
    public void withdrawPressed(View v) {
        if (bank.accountList.get(cardChosen).getCancelled().equals("no")) {
            if (useRegion.getText().toString().equals(bank.accountList.get(cardChosen).getRegion())) {
                double amount = Double.parseDouble(withdrawAmount.getText().toString());
                if (amount <= bank.accountList.get(cardChosen).getWithdrawLimit() &&
                        amount <= bank.accountList.get(cardChosen).getBalance()) {
                    double newBalance = bank.accountList.get(cardChosen).getBalance() - amount;
                    bank.accountList.get(cardChosen).setBalance(newBalance);

                    bank.transactionList.add(new Transaction(bank.accountList.get(cardChosen).getAccountNumber(),
                            "Money withdrawn from card: " + amount));
                    Toast.makeText(CardWithdrawPay.this, "Withdraw done successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CardWithdrawPay.this, "Withdraw not done. Balance is too low.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CardWithdrawPay.this, "Incompetent using region for the card.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CardWithdrawPay.this, "Card is cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    // checks if card is not cancelled and can be used in written area/region
    //makes the payment and adds transaction if terms above are ok
    public void payPressed(View v) {
        double amount = Double.parseDouble(paymentAmount.getText().toString());
        if (bank.accountList.get(cardChosen).getCancelled().equals("no")) {
            if (useRegion.getText().toString().equals(bank.accountList.get(cardChosen).getRegion())) {
                if (bank.accountList.get(cardChosen).getAccountType().equals("Credit account")) {
                    if (amount <= bank.accountList.get(cardChosen).getCreditLimit()) {
                        double newCredit = bank.accountList.get(cardChosen).getCreditLimit() - amount;
                        bank.accountList.get(cardChosen).setCreditLimit(newCredit);
                        bank.transactionList.add(new Transaction(bank.accountList.get(cardChosen).getAccountNumber(),
                                "Credit payment done, amount: " + amount));
                        Toast.makeText(CardWithdrawPay.this, "Payment done successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CardWithdrawPay.this, "Credit limit too low.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (amount <= bank.accountList.get(cardChosen).getBalance()) {
                        if (amount <= bank.accountList.get(cardChosen).getPayLimit()) {
                            double newBalance = bank.accountList.get(cardChosen).getBalance() - amount;
                            bank.accountList.get(cardChosen).setBalance(newBalance);
                            bank.transactionList.add(new Transaction(bank.accountList.get(cardChosen).getAccountNumber(),
                                    "Debit payment done, amount: " + amount));
                            Toast.makeText(CardWithdrawPay.this, "Payment done successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CardWithdrawPay.this, "Pay limit is too low.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CardWithdrawPay.this, "Balance too low.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(CardWithdrawPay.this, "Incompetent using region for the card.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CardWithdrawPay.this, "Card is cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        //equals user being admin
        if (aORn == 1) {
            Intent intent = new Intent(CardWithdrawPay.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            System.out.println(username);
            System.out.println(aORn);
            startActivity(intent);
        //equals user being normal
        } else {
            Intent intent = new Intent(CardWithdrawPay.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}
