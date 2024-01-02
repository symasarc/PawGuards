package com.example.pawguards;

public class Donation {

    private String title;
    private float donationAmount;

    public Donation(String title, float donationAmount) {
        this.title = title;
        this.donationAmount = donationAmount;
    }

    public Donation() {
    }

    public String getTitle() {
        return title;
    }

    public float getDonationAmount() {
        return donationAmount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDonationAmount(float donationAmount) {
        this.donationAmount = donationAmount;
    }
}