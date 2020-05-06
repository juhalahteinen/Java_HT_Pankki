package com.example.ht;

import android.content.Context;
import android.graphics.Region;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private static Bank bank = new Bank();

    public static Bank getInstance() {
        return bank;
    }

    String chosenAccount;
    String chosenRegion;
    String chosenAccountType;
    String chosenCard;

    int chosenListNumber;
    int chosenCardArray;

    ArrayList<Users> userList = new ArrayList<>();
    ArrayList<Account> accountList = new ArrayList<>();
    ArrayList<Transaction> transactionList = new ArrayList<>();

    // spinner for choosing accounts
    public int accountSpinner(String username, Spinner spinner, Context context) {
        final List<String> Accounts = new ArrayList<>();
        Accounts.add("");
        for (int i = 0; i < accountList.size(); i++) {
            if (username.equals(accountList.get(i).getUserOwner())) {
                Accounts.add(accountList.get(i).getAccountNumber());
            }
        }

        ArrayAdapter<String> AccountAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Accounts);
        AccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(AccountAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    chosenAccount = (String) parent.getItemAtPosition(position);

                    for (int j = 0; j < accountList.size(); j++) {
                        if (chosenAccount.equals(accountList.get(j).getAccountNumber())) {
                            chosenListNumber = j;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // returns array about chosen account
        return chosenListNumber;
    }

    // spinner made for choosing (card) using areas/regions
    public String regionSpinner(Spinner spinner, Context context) {
        List<String> Regions = new ArrayList<>();

        Regions.add("");
        Regions.add("Finland");
        Regions.add("Europe");
        Regions.add("America");
        Regions.add("Asia");
        Regions.add("Australia");

        ArrayAdapter<String> RegionAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Regions);
        RegionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(RegionAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    chosenRegion = (String) parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (chosenRegion == null) {
            return null;
        } else {
            return chosenRegion;
        }
    }

    // spinner made for choosing account type
    public String accountTypeSpinner(Spinner spinner, Context context) {
        List<String> AccountTypes = new ArrayList<>();

        AccountTypes.add("");
        AccountTypes.add("Debit account");
        AccountTypes.add("Credit account");

        ArrayAdapter<String> AccountTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, AccountTypes);
        AccountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(AccountTypeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    chosenAccountType = (String) parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (chosenAccountType == null) {
            return null;
        } else {
            return chosenAccountType;
        }
    }

    // spinner for selecting or using cards
    // card selection happens by account number --> checks if card exists
    public int cardSpinner(String username, Spinner spinner, Context context) {
        List<String> Cards = new ArrayList<>();

        for (int k = 0; k < accountList.size(); k++) {
            if (username.equals(accountList.get(k).getUserOwner())) {
                if (accountList.get(k).getCard().equals(("yes"))) {
                    Cards.add(accountList.get(k).getAccountNumber());
                }
            }
        }

        ArrayAdapter<String> CardAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Cards);
        CardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(CardAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    chosenCard = (String) parent.getItemAtPosition(position).toString();

                    for (int j = 0; j < accountList.size(); j++) {
                        if (chosenCard.equals(accountList.get(j).getAccountNumber())) {
                            chosenCardArray = j;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return chosenCardArray;
    }
}