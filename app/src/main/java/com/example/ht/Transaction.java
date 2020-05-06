package com.example.ht;

public class Transaction {

    private String accountNumber, textnote;

    public Transaction (String accNum, String tn) {
        accountNumber = accNum;
        textnote = tn;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTextnote() { return textnote; }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setTextnote(String textnote) {
        this.textnote = textnote;
    }
}
