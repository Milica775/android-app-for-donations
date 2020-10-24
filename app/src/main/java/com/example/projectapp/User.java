package com.example.projectapp;

import java.util.Comparator;
import java.util.List;

public class User {

    public String email;
    public int sumOfDonation;
    public String firstName;
    public String lastName;

    public User() {

    }

    public User(String email, int sumOfDonation, String firstName, String lastName) {
        this.email = email;
        this.sumOfDonation = sumOfDonation;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSumOfDonation() {
        return sumOfDonation;
    }

    public void setSumOfDonation(int sumOfDonation) {
        this.sumOfDonation = sumOfDonation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
