package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AccountTransactions extends AppCompatActivity {

    Bank bank = Bank.getInstance();

    int aORn;
    String username;
    int placeOnList;

    TextView transactionWindow;
    TextView balance;
    TextView optionalCLimit;
    TextView viewCLimit;
    Spinner spinAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_transactions);

        // gets who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        balance = (TextView) findViewById(R.id.accountBalance);
        optionalCLimit = (TextView) findViewById(R.id.cLimitOptT);
        viewCLimit = (TextView) findViewById(R.id.viewCLimit);
        transactionWindow = (TextView) findViewById(R.id.transactionView);
        spinAcc = findViewById(R.id.accountSPinner);

        // checks if accounts exist and gives info if doesn't
        if (bank.accountList.size() == 0) {
            Toast.makeText(this, "No transactions.",Toast.LENGTH_LONG).show();
        } else {
            // makes existing accounts available for spinner
            bank.accountSpinner(username, spinAcc, this);
        }
    }


    public void showTransactions(View v) {
        placeOnList = bank.accountSpinner(username, spinAcc, AccountTransactions.this);
        String accountBalance = String.valueOf(bank.accountList.get(placeOnList).getBalance());
        String accountCredit = String.valueOf(bank.accountList.get(placeOnList).getCreditLimit());
        balance.setText(accountBalance);
        if (bank.accountList.get(placeOnList).getAccountType().equals("Credit account")) {
            optionalCLimit.setText("Credit limit: ");
            viewCLimit.setText(accountCredit);
        }

        StringBuilder transactions = new StringBuilder("######################\n\n");

        for (int i = 0; i < bank.transactionList.size(); i++) {
            if (bank.transactionList.get(i).getAccountNumber().equals(bank.accountList.get(placeOnList).getAccountNumber())) {
                transactions.append(bank.transactionList.get(i).getTextnote()).append("\n\n########\n\n");
            }
        }
        // scrolling method in case of there are many transactions to be shown
        transactionWindow.setMovementMethod(new ScrollingMovementMethod());
        transactionWindow.setText(transactions);
    }

    @Override
    public void onBackPressed() {
        // equals to user being admin
        if (aORn == 1) {
            Intent intent = new Intent(AccountTransactions.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            System.out.println(username);
            System.out.println(aORn);
            startActivity(intent);

        // equals to user being normal user
        } else {
            Intent intent = new Intent(AccountTransactions.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}