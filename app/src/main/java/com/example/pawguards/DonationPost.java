package com.example.pawguards;

import android.net.Uri;

public class DonationPost {

    //private String image;
    private String title;
    private String description;
    private float raisedAmount;
    private float goalAmount;
    private String imageLink;


    public DonationPost(String title, String description, String image, float raisedAmount, float goalAmount, String imageLink) {
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.raisedAmount = raisedAmount;
        this.goalAmount = goalAmount;
    }


    public DonationPost() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public float getRaisedAmount() { return raisedAmount; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) { this.description = description; }

    public void setImageLink(String image) {
        this.imageLink = image;
    }

    public void setRaisedAmount(float raisedAmount) { this.raisedAmount = raisedAmount; }

    public float getGoalAmount() { return goalAmount; }

    public void setGoalAmount(float goalAmount) { this.goalAmount = goalAmount; }

    @Override
    public String toString() {
        return "DonationPost{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                //", image='" + image + '\'' +
                ", raisedAmount=" + raisedAmount +
                ", goalAmount=" + goalAmount +
                '}';
    }
}
