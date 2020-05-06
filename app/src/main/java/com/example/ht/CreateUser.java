package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateUser extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$");
    // password requirements are defined above
    // at least one number
    // at least one lowercase letter
    // at least one uppercase letter
    // at least one special character
    // no whitespaces allowed
    // at least 12 characters


    EditText username;
    EditText password;
    EditText name;
    EditText address;
    EditText country;
    Context context = null;

    TextView error;

    //access for Bank class
    Bank bank = new Bank().getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        username = (EditText) findViewById(R.id.userID_Log);
        password = (EditText) findViewById(R.id.userPassword_Log);
        name = (EditText) findViewById(R.id.userName);
        address = (EditText) findViewById(R.id.userAddress);
        country = (EditText) findViewById(R.id.userCountry);
        error = (TextView) findViewById(R.id.errorOutput);
        context = CreateUser.this;
    }

    // adding information to user profile
    public void createUser(View v) {
        if (validatePassword() == true) {
            error.setText("User created successfully");
            bank.userList.add(new Users(username.getText().toString(),
                    password.getText().toString(), name.getText().toString(),
                    address.getText().toString(), country.getText().toString()));
            Intent intent = new Intent(CreateUser.this, LogIn.class);
            startActivity(intent);
        } else {
            System.out.println("Maybe something went wrong. :(");
        }
    }

    //checks if give password fulfills given requirements
    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            error.setText("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            error.setText("Password too weak");
            return false;
        } else {
            return true;
        }
    }
}
