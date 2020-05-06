package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminActions extends AppCompatActivity {

    int aORn;
    String username;

    //gets access to Bank class
    Bank bank = Bank.getInstance();

    TextView chosenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actions);

        // gets info who has logged in
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");

        chosenUser = (TextView) findViewById(R.id.chosenUser);
        chosenUser.setText(username);
  }

    public void manageUserInformation(View v) {
        Intent intent = new Intent(AdminActions.this, UserSettings.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void manageAccountsCards(View v) {
        Intent intent = new Intent(AdminActions.this, AdminAccountCardControl.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void createAccountAdmin(View v) {
        Intent intent = new Intent(AdminActions.this, CreateAccount.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void createCardAdmin(View v) {
        Intent intent = new Intent(AdminActions.this, CreateCard.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void manageCardsAdmin(View v) {
        Intent intent = new Intent(AdminActions.this, CardSettings.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void userTransactionsAdmin(View v) {
        Intent intent = new Intent(AdminActions.this, AccountTransactions.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    public void removeUserAdmin(View v) {
        if (bank.userList.size() != 0) {
            for (int i = 0; i < bank.accountList.size(); i++) {
                if (bank.accountList.get(i).getUserOwner().equals(username)) {
                    bank.accountList.remove(i);
                }
            }
            for (int k = 0; k < bank.userList.size(); k++) {
                if (username.equals(bank.userList.get(k).getUserName())) {
                    bank.userList.remove(k);
                }
            }
            chosenUser.setText(("User has been remove. Please return to menu."));
        } else {
            chosenUser.setText(("No users exist."));
        }
    }

    // goes back to menu for choosing user to inspect
    public void backToAdminMenu(View v) {
        Intent intent = new Intent(AdminActions.this, AdminMenu.class);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

    // logs out admin and starts the basic log in view
    public void logOutAdmin(View v) {
        Intent intent = new Intent(AdminActions.this, LogIn.class);
        startActivity(intent);
    }
}
