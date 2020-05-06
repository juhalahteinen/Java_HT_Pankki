package com.example.ht;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.time.LocalDate;


public class AccountTransfer extends AppCompatActivity {

    int placeOnListInt;

    //gets access to Bank class
    Bank bank = Bank.getInstance();

    int aORn;
    String username;

    TextView information;
    TextView accountBalance;
    TextView type;
    EditText transAccount;
    EditText amount;
    Spinner accountSp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_transfer);

        // gets who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        information = (TextView) findViewById(R.id.textInfo);
        accountBalance = (TextView) findViewById(R.id.accBalance);
        type = (TextView) findViewById(R.id.accType);
        transAccount = (EditText) findViewById(R.id.accountNumber);
        amount = (EditText) findViewById(R.id.moneyAmount);
        accountSp = (Spinner) findViewById(R.id.accountSp);

        // checks if accounts exist and gives info if doesn't
        if (bank.accountList.size() == 0) {
            Toast.makeText(AccountTransfer.this, "No accounts exist, please return to create new.", Toast.LENGTH_SHORT).show();
        } else {
            // makes existing accounts available for spinner
            bank.accountSpinner(username, accountSp, this);
        }

        // made to check if account is frozen, if does gives info
        // else pops up balance and account type
        transAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOnListInt = bank.accountSpinner(username, accountSp, AccountTransfer.this);

                if (bank.accountList.get(placeOnListInt).getFrozen().equals("yes")) {
                    Toast.makeText(AccountTransfer.this, "Account is frozen. Transfer can't be made.", Toast.LENGTH_SHORT).show();
                    information.setText("Account is frozen.");
                } else {
                    String balance = String.valueOf(bank.accountList.get(placeOnListInt).getBalance());
                    accountBalance.setText(balance);
                    type.setText(bank.accountList.get(placeOnListInt).getAccountType());
                }
            }
        });
    }

    // makes the transfer and adds information to transactions
    public void confirmTransfer(View v) {
        double transferAmount = Double.parseDouble(amount.getText().toString());
        // prevents transferring to same account
        if (bank.accountList.get(placeOnListInt).getAccountNumber().equals(transAccount.getText().toString()) == false) {
            //if credit account
            if (bank.accountList.get(placeOnListInt).getAccountType().equals("Credit account")) {
                double creditStatus = bank.accountList.get(placeOnListInt).getCreditLimit();
                //checks if only balance is enough to cover the transfer (costs)
                if (transferAmount < (bank.accountList.get(placeOnListInt).getBalance())) {
                    double newBalance = bank.accountList.get(placeOnListInt).getBalance() - transferAmount;
                    bank.accountList.get(placeOnListInt).setBalance(newBalance);
                    Toast.makeText(AccountTransfer.this, "Transfer has been made.", Toast.LENGTH_SHORT).show();

                    bank.transactionList.add(new Transaction(bank.accountList.get(placeOnListInt).getAccountNumber(), "From account: " +
                            bank.accountList.get(placeOnListInt).getAccountNumber() + "\n paid: " + transferAmount + "\nFor account: " + transAccount.getText().toString()));
                } else {
                    //checks is balance + credit is enough and then sets balance for 0 and subtracts rest of credit
                    //credit limit has standard value 0 if card is not created
                    if (transferAmount < (bank.accountList.get(placeOnListInt).getBalance()) + bank.accountList.get(placeOnListInt).getCreditLimit()) {
                        bank.accountList.get(placeOnListInt).setBalance(0);
                        double difference = transferAmount - bank.accountList.get(placeOnListInt).getBalance();
                        double newCredit = bank.accountList.get(placeOnListInt).getCreditLimit() - difference;
                        bank.accountList.get(placeOnListInt).setCreditLimit(newCredit);
                        Toast.makeText(AccountTransfer.this, "Transfer has been made.", Toast.LENGTH_SHORT).show();

                        bank.transactionList.add(new Transaction(bank.accountList.get(placeOnListInt).getAccountNumber(), "From account: " +
                                bank.accountList.get(placeOnListInt).getAccountNumber() + "\n paid: " + transferAmount + "\nfor account:" + transAccount.getText().toString()));
                    } else {
                        Toast.makeText(AccountTransfer.this, "Not enough balance or credit.", Toast.LENGTH_SHORT).show();
                    }
                }
                //if debit account
            } else {
                if (transferAmount < (bank.accountList.get(placeOnListInt).getBalance())) {
                    double newBalance = bank.accountList.get(placeOnListInt).getBalance() - transferAmount;
                    bank.accountList.get(placeOnListInt).setBalance(newBalance);
                    Toast.makeText(AccountTransfer.this, "Transfer has been made.", Toast.LENGTH_SHORT).show();

                    bank.transactionList.add(new Transaction(bank.accountList.get(placeOnListInt).getAccountNumber(), "From account: " +
                            bank.accountList.get(placeOnListInt).getAccountNumber() + "\n paid: " + transferAmount + "\nfor account:" + transAccount.getText().toString()));
                } else {
                    Toast.makeText(AccountTransfer.this, "Not enough balance.", Toast.LENGTH_SHORT).show();
                }
            }

            // looks if the receiver got account in same bank --> adds money to that account
            for (int i = 0; i > bank.accountList.size(); i++) {
                if (bank.accountList.get(i).equals(transAccount.getText().toString())) {
                    double newBalanceTransfer = (bank.accountList.get(i).getBalance() + transferAmount);
                    bank.accountList.get(i).setBalance(newBalanceTransfer);

                    bank.transactionList.add(new Transaction(transAccount.getText().toString().trim(), "Received from account: " +
                            bank.accountList.get(placeOnListInt).getAccountNumber() + "\namount: " + transferAmount));
                }
            }

        //user tried to transfer to the same account
        } else {
            Toast.makeText(AccountTransfer.this, "Can't transfer to same account.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        // equals to user being admin
        if (aORn == 1) {
            Intent intent = new Intent(AccountTransfer.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            System.out.println(username);
            System.out.println(aORn);
            startActivity(intent);

        // equals to user being normal user
        } else {
            Intent intent = new Intent(AccountTransfer.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}

