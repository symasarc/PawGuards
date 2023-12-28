package com.example.pawguards.fragments;

public class Campaign {

    private String title;
    private String description;
    private String amountRaised;
    private int imageResourceId;

    public Campaign(String title, String description, String amountRaised, int imageResourceId) {
        this.title = title;
        this.description = description;
        this.amountRaised = amountRaised;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAmountRaised() {
        return amountRaised;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}