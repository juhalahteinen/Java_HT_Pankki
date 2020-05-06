package com.example.ht;

public class Account {
    private String userOwner, accountNumber, accountType, card, region, frozen, cancelled;
    private double balance, payLimit, withdrawLimit, creditLimit;

    public Account(String o, String accType, String accNum, double b, double pLimit, String r, String c, double wLimit, String f, String can, double cLimit) {
        userOwner = o;
        accountNumber = accNum;
        accountType = accType;
        card = c;
        region = r;
        frozen = f;
        cancelled = can;
        balance = b;
        payLimit = pLimit;
        withdrawLimit = wLimit;
        creditLimit = cLimit;
    }
    @Override
    public String toString() {

        return accountNumber;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCard() {
        return card;
    }

    public String getRegion() {
        return region;
    }

    public String getFrozen() {
        return frozen;
    }

    public String getCancelled() {
        return cancelled;
    }

    public double getBalance() {
        return balance;
    }

    public double getPayLimit() {
        return payLimit;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public void setPayLimit(double payLimit) {
        this.payLimit = payLimit;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
}
