package com.example.ht;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Users {
    private String userName, password, name, address, country;
    Bank bank = new Bank().getInstance();

    public Users (String un, String p, String n, String a, String c) {
        userName = un;
        password = p;
        name = n;
        address = a;
        country = c;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
