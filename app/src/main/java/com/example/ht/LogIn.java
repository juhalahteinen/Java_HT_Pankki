package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class LogIn extends AppCompatActivity {
    Bank bank = Bank.getInstance();
    EditText user;
    EditText password;
    EditText keycode;

    TextView errorKey;
    TextView vCodeInfo;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        user = (EditText) findViewById(R.id.userID_Log);
        password = (EditText) findViewById(R.id.userPassword_Log);
        keycode = (EditText) findViewById(R.id.keyCode);
        errorKey = (TextView) findViewById(R.id.errorKeyBox);
        vCodeInfo = (TextView) findViewById(R.id.verCodeInfo);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Random random = new Random();
                //+100000 to be sure it's a 6-digit number --> range from 0-899999 + 100000 --> 100000 - 999999
                int keyNumber = random.nextInt( 899999) + 100000;
                final String keyNumberString = String.valueOf(keyNumber);

                if (user.getText().toString().equals("Admin")) {
                    if (password.getText().toString().equals("Admin1asd@@@")) {
                        j = 1;
                        errorKey.setText(keyNumberString);// pops up a 6-digit verification code
                        vCodeInfo.setText(("Your verification code:"));
                    }
                } else {
                    for (int i = 0; i < bank.userList.size(); i++) {
                        if (user.getText().toString().equals(bank.userList.get(i).getUserName())) {
                            if (password.getText().toString().equals(bank.userList.get(i).getPassword())) {
                                errorKey.setText(keyNumberString);
                                vCodeInfo.setText(("Your verification code:"));
                                j = 0;
                            }
                        }
                    }
                }
            }
        });
    }


    // shifts to create users
    public void onClickCreateUser(View v) {
        Intent intent = new Intent(LogIn.this, CreateUser.class);
        startActivity(intent);
    }

    public void onClickLogIn(View v) {

        //creates a 6-digit verification code after login button has been pressed
        Random random = new Random();
        int keyNumber = random.nextInt(999999);
        String keyCode = keycode.getText().toString();
        String ErrorKey = errorKey.getText().toString();

        //takes user to admin view if username and password fulfill the (admin) requirements
        if (user.getText().toString().equals("Admin")) {
            if (password.getText().toString().equals("Admin1asd@@@")) {
                if (ErrorKey.equals(keyCode) && (j == 1)) {
                    Intent intent = new Intent(LogIn.this, AdminMenu.class);
                    intent.putExtra("AdminOrNormal", 1);
                    startActivity(intent);
                }
            }
        // moves to normal user view
        } else
            for (int i = 0; i < bank.userList.size(); i++) {
                if (user.getText().toString().equals(bank.userList.get(i).getUserName())) {
                    if (password.getText().toString().equals(bank.userList.get(i).getPassword())) {
                        if (ErrorKey.equals(keyCode) && (j == 0)) {
                            Intent intent = new Intent(LogIn.this, MainActivity.class);
                            String username = user.getText().toString();
                            intent.putExtra("username", username);
                            intent.putExtra("AdminOrNormal", 0);
                            startActivity(intent);
                        }
                    } else {
                        errorKey.setText("Wrong user ID or password");
                }
            }
        }
    }
}