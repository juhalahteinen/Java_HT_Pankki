package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CardSettings extends AppCompatActivity {

    //gets access to Bank class
    Bank bank = Bank.getInstance();

    String chosenRegion = null;
    int cardArray;

    String username;
    int aORn;

    TextView pLimitOld;
    TextView wLimitOld;
    TextView cLimitOld;
    TextView regOld;
    TextView information;

    EditText pLimitNew;
    EditText wLimitNew;
    EditText cLimitNew;

    Spinner SpinReg;
    Spinner SpinAccountCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_settings);

        SpinReg = findViewById(R.id.selectRegionConstraint);
        SpinAccountCard = findViewById(R.id.selectCardToAlter);

        pLimitOld = (TextView) findViewById(R.id.cPLimit);
        wLimitOld = (TextView) findViewById(R.id.cWLimit);
        cLimitOld = (TextView) findViewById(R.id.cCLimit);
        regOld = (TextView) findViewById(R.id.cRegion);
        information = (TextView) findViewById(R.id.textView23);

        pLimitNew = (EditText) findViewById(R.id.alterPaymentLimit);
        wLimitNew = (EditText) findViewById(R.id.alterWithdrawLimit);
        cLimitNew = (EditText) findViewById(R.id.alterCreditLimit);

        // gets who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        int cardCount = 0;

        // checking if cards exists for account
        for (int i = 0; i < bank.accountList.size(); i++) {
            if (bank.accountList.get(i).getCard().equals("yes")) {
                cardCount = cardCount + 1;
            }
        }

        if (cardCount == 0) {
            information.setText("No bank cards exist, please return create new");
        } else {
            // spinners for choosing region and card
            bank.regionSpinner(SpinReg, this);
            bank.cardSpinner(username, SpinAccountCard, this);
        }
    }
    
    public void showOldSettings(View v) {
        cardArray = bank.cardSpinner(username, SpinAccountCard, CardSettings.this);
        // if a credit card, will also set info for credit limit
        if (bank.accountList.get(cardArray).getCreditLimit() > 0) {
            pLimitOld.setText(String.valueOf(bank.accountList.get(cardArray).getPayLimit()));
            wLimitOld.setText(String.valueOf(bank.accountList.get(cardArray).getWithdrawLimit()));
            cLimitOld.setText(String.valueOf(bank.accountList.get(cardArray).getCreditLimit()));
            regOld.setText(bank.accountList.get(cardArray).getRegion());

        } else { // if a debit card
            pLimitOld.setText(String.valueOf(bank.accountList.get(cardArray).getPayLimit()));
            wLimitOld.setText(String.valueOf(bank.accountList.get(cardArray).getWithdrawLimit()));
            cLimitOld.setText("");
            regOld.setText(bank.accountList.get(cardArray).getRegion());
        }
    }

    // checks information that has changed saves new ones
    public void saveNewSettings(View v) {
        chosenRegion = bank.regionSpinner(SpinReg, CardSettings.this);

        if (!pLimitNew.getText().toString().equals("")) {
            double payLimit = Double.parseDouble(pLimitNew.getText().toString());
            bank.accountList.get(cardArray).setPayLimit(payLimit);
            pLimitOld.setText(String.valueOf(payLimit));
        } if (!wLimitNew.getText().toString().equals("")) {
            double withdrawLimit = Double.parseDouble(wLimitNew.getText().toString());
            bank.accountList.get(cardArray).setWithdrawLimit(withdrawLimit);
            wLimitOld.setText(String.valueOf(withdrawLimit));
        } if (!cLimitNew.getText().toString().equals("")) {
            double creditLimit = Double.parseDouble(cLimitNew.getText().toString());
            bank.accountList.get(cardArray).setCreditLimit(creditLimit);
            cLimitOld.setText(String.valueOf(creditLimit));
        } if (chosenRegion != "") {
            bank.accountList.get(cardArray).setRegion(chosenRegion);
            regOld.setText(chosenRegion);
        }
        information.setText("Settings updated successfully");
    }

    @Override
    public void onBackPressed() {
        //equals to user being admin
        if (aORn == 1) {
            Intent intent = new Intent(CardSettings.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }

        //equals user being normal user
        else {
            Intent intent = new Intent(CardSettings.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}
