package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Bank bank = Bank.getInstance();
    Context context;
    String username;
    int aORn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (String) getIntent().getSerializableExtra("username");
        aORn = (int) getIntent().getSerializableExtra("AdminOrNormal");
        context = MainActivity.this;
    }

    public void createAccountMain(View v) {
        Intent intent = new Intent(MainActivity.this, CreateAccount.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
    }

   public void createCardMain(View v) {
       Intent intent = new Intent(MainActivity.this, CreateCard.class);
       intent.putExtra("username", username);
       intent.putExtra("AdminOrNormal", aORn);
       startActivity(intent);
   }

   public void changeCardSettingsMain(View v) {
       Intent intent = new Intent(MainActivity.this, CardSettings.class);
       intent.putExtra("username", username);
       intent.putExtra("AdminOrNormal", aORn);
       startActivity(intent);
   }

   public void changeUserSettingsMain(View v) {
       Intent intent = new Intent(MainActivity.this, UserSettings.class);
       intent.putExtra("username", username);
       intent.putExtra("AdminOrNormal", aORn);
       startActivity(intent);
   }

   public void changePasswordMain(View v) {
       Intent intent = new Intent(MainActivity.this, ChangePassword.class);
       intent.putExtra("username", username);
       intent.putExtra("AdminOrNormal", aORn);
       startActivity(intent);
   }

   public void cardPaymentWithdrawMain(View v) {
       Intent intent = new Intent(MainActivity.this, CardWithdrawPay.class);
       intent.putExtra("username", username);
       intent.putExtra("AdminOrNormal", aORn);
       startActivity(intent);
   }

   //write a csv file about user information and then logs out
   public void LogOutMain(View v) throws IOException {
       String fileName = "User information.csv";
       OutputStreamWriter osw;
       osw = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

       osw.write("Username,Password\n");
       for (Users user : bank.userList) {
           osw.write(user.getUserName() + ",");
           osw.write(user.getPassword() + "\n");
       }

       osw.close();
       Intent intent = new Intent(MainActivity.this, LogIn.class);
       startActivity(intent);
    }

   public void viewTransactionsMain(View v) {
       Intent intent = new Intent(MainActivity.this, AccountTransactions.class);
       intent.putExtra("username", username);
       intent.putExtra("AdminOrNormal", aORn);
       startActivity(intent);
   }

   public void depositMoneyMain(View v) {
        Intent intent = new Intent(MainActivity.this, AccountDeposit.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
   }

   public void makeTransferMain(View v) {
        Intent intent = new Intent(MainActivity.this, AccountTransfer.class);
        intent.putExtra("username", username);
        intent.putExtra("AdminOrNormal", aORn);
        startActivity(intent);
   }
}