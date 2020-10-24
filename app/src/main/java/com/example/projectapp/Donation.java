package com.example.projectapp;

public class Donation {

    public String address;
    public  int numOfDonation;
    public String email;
    public String date;
    public String id;

    public Donation() {

    }

    public Donation(String address, int numOfDonation, String email, String date, String id) {
        this.address = address;
        this.numOfDonation = numOfDonation;
        this.email = email;
        this.date = date;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumOfDonation() {
        return numOfDonation;
    }

    public void setNumOfDonation(int numOfDonation) {
        this.numOfDonation = numOfDonation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
