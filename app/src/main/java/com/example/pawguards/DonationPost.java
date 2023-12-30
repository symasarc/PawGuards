package com.example.pawguards;

public class DonationPost {

    private String title;
    private String description;
    private String image;
    private float raisedAmount;

    public DonationPost(String title, String description, String image, float raisedAmount) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.raisedAmount = raisedAmount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public float getRaisedAmount() { return raisedAmount; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) { this.description = description; }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRaisedAmount(float raisedAmount) { this.raisedAmount = raisedAmount; }

    @Override
    public String toString() {
        return "DonationPost{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", raisedAmount='" + raisedAmount + '\'' +
                '}';
    }
}
