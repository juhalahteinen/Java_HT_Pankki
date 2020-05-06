package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AccountDeposit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // gets access to Bank class
    Bank bank = Bank.getInstance();

    String username;
    int aORn;

    TextView balance;
    TextView type;
    Button confirmButton;
    EditText amount;
    int accountPlaceInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_deposit);
        Spinner accountSpinner = findViewById(R.id.spinner);
        accountSpinner.setOnItemSelectedListener(this);
        balance = findViewById(R.id.balance);
        type = findViewById(R.id.type);
        confirmButton = findViewById(R.id.confirm);
        amount = findViewById(R.id.amount);

        // gets who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        // checks if accounts exist and gives error if doesn't
        if (bank.accountList.isEmpty()) {
            Toast.makeText(AccountDeposit.this, "No accounts exist, please return to create new.",Toast.LENGTH_SHORT).show();
        }

        // setting up a spinner to select the account to deposit to
        ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_spinner_item, bank.accountList);
        accountSpinner.setAdapter(adapter);
    }

    // shows account's balance and (account) type
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        balance.setText(""+bank.accountList.get(position).getBalance());
        type.setText(""+bank.accountList.get(position).getAccountType());
        accountPlaceInList = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // adds money for the account and adds deposit transaction to transactions
    public void onConfirmPressed(View v) {
        try {
            double deposit = Double.parseDouble(amount.getText().toString());
            double newBalance = (deposit + bank.accountList.get(accountPlaceInList).getBalance());
            bank.accountList.get(accountPlaceInList).setBalance(newBalance);
            String newBalanceString = String.valueOf(newBalance);
            balance.setText(newBalanceString);

            bank.transactionList.add(new Transaction(bank.accountList.get(accountPlaceInList).getAccountNumber(), "Money deposit: " + deposit));
            Toast.makeText(AccountDeposit.this, "Deposit successful.",Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(AccountDeposit.this, "Invalid amount input: "+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        // equals to user being admin
        if (aORn == 1) {
            Intent intent = new Intent(AccountDeposit.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }

        // equals to user being normal user
        else {
            Intent intent = new Intent(AccountDeposit.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}
