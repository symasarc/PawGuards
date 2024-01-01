package com.example.pawguards;

import com.google.firebase.firestore.DocumentReference;

public class Animal {
    private String name;
    private int age;
    private String type;
    private String description;
    private String gender;
    private boolean isAdopted;
    private DocumentReference whoAdopted=null;
    private DocumentReference whoPosted=null;
    private String image;
    private String title;
    private String location;


    public Animal() {
    }

    public Animal(String name, int age, String type, String description, String gender, boolean isAdopted, DocumentReference whoAdopted, DocumentReference whoPosted, String image, String title, String location) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.description = description;
        this.gender = gender;
        this.isAdopted = isAdopted;
        this.whoAdopted = whoAdopted;
        this.whoPosted = whoPosted;
        this.image = image;
        this.title = title;
        this.location = location;
    }

    public Animal(String name, int age, String type, String description, String gender, boolean isAdopted, DocumentReference whoAdopted, DocumentReference whoPosted) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.description = description;
        this.gender = gender;
        this.isAdopted = isAdopted;
        this.whoAdopted = whoAdopted;
        this.whoPosted = whoPosted;
    }

    public Animal(String name, String description, int age, String type, String gender) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.description = description;
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public String getStringAge() {
        return String.valueOf(age);
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isAdopted() {
        return isAdopted;
    }

    public DocumentReference getWhoAdopted() {
        return whoAdopted;
    }

    public void setAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    public void setWhoAdopted(DocumentReference whoAdopted) {
        this.whoAdopted = whoAdopted;
    }

    public DocumentReference getWhoPosted() {
        return whoPosted;
    }

    public void setWhoPosted(DocumentReference whoPosted) {
        this.whoPosted = whoPosted;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", gender='" + gender + '\'' +
                ", isAdopted=" + isAdopted +
                ", whoAdopted=" + whoAdopted +
                ", whoPosted=" + whoPosted +
                '}';
    }
}
