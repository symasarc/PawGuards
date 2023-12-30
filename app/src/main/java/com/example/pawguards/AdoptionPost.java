package com.example.pawguards;

public class AdoptionPost {

    private String title;
    private String description;
    private String location;
    private String image;
    private String uid;
    private String date;
    private String time;
    private Animal animal;

    public AdoptionPost(String title, String description, String location, String image, String uid, String date, String time, Animal animal) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.image = image;
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.animal = animal;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() { return location; }

    public String getImage() {
        return image;
    }

    public String getUid() {
        return uid;
    }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public Animal getAnimal() { return animal; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) { this.description = description; }

    public void setLocation(String location) { this.location = location; }

    public void setImage(String image) {
        this.image = image;
    }
}
