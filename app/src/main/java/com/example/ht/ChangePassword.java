package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {

    //pattern for defining the password requirements (given in (practical exercise) assignment)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$");

    EditText oldPassword;
    EditText newPassword1;
    EditText newPassword2;
    Button saveButton;

    int userChosen;
    String username;
    int aORn;

    // gets access to Bank class
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword1 = (EditText) findViewById(R.id.newPassword1);
        newPassword2 = (EditText) findViewById(R.id.newPassword2);
        saveButton = (Button) findViewById(R.id.saveButton);

        //gets info about who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");
    }

    public void saveButtonPressed(View v) {
        if (validatePassword()) {
            int i = -1;

            for (Users user : bank.userList) {
                i++;
                //find active user with for loop
                if (username.equals(bank.userList.get(i).getUserName())) {
                    userChosen = i;
                    if (oldPassword.getText().toString().trim().equals(bank.userList.get(userChosen).getPassword())) {
                        bank.userList.get(userChosen).setPassword(newPassword1.getText().toString().trim());
                        Toast.makeText(ChangePassword.this, "Password changed.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(ChangePassword.this, "Old password is wrong.",Toast.LENGTH_SHORT).show();

        }
    }

    private boolean validatePassword() {
        String passwordInput = newPassword1.getText().toString().trim();

        if (passwordInput.isEmpty() || oldPassword.getText().toString().trim().isEmpty() || newPassword2.getText().toString().trim().isEmpty()) {
            Toast.makeText(ChangePassword.this, "Field can't be empty",Toast.LENGTH_SHORT).show();

            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Toast.makeText(ChangePassword.this, "Password is too weak.",Toast.LENGTH_SHORT).show();

            return false;
        } else if (!newPassword1.getText().toString().trim().equals(newPassword2.getText().toString().trim())) {
            Toast.makeText(ChangePassword.this, "Old and new password don't match.",Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        // equals to user being admin
        if (aORn == 1) {
            Intent intent = new Intent(ChangePassword.this, AdminActions.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }

        // user being a normal user
        else {
            Intent intent = new Intent(ChangePassword.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("AdminOrNormal", aORn);
            startActivity(intent);
        }
    }
}
