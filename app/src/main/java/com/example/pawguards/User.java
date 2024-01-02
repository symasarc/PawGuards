package com.example.pawguards;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class User {
    private String name;
    private String surname;
    private String email;
    private String uid;
    private List<Donation> donationsMade;
    private List<DocumentReference> animalsAdopted;
    private List<DocumentReference> adoptionPosts;
    private String country;
    private String gender;
    private String profilePicture;
    private int moneyRemaining;

    public User() {
    }


    public User(String profilePicture, String name, String surname, String email, String uid, List<Donation> donationsMade, List<DocumentReference> animalsAdopted, List<DocumentReference> adoptionPosts, String country, String gender, int moneyRemaining) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.uid = uid;
        this.donationsMade = donationsMade;
        this.animalsAdopted = animalsAdopted;
        this.adoptionPosts = adoptionPosts;
        this.country = country;
        this.gender = gender;
        this.moneyRemaining = moneyRemaining;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUid() {
        return uid;
    }

    public List<Donation> getDonationsMade() {
        return donationsMade;
    }

    public List<DocumentReference> getAnimalsAdopted() {
        return animalsAdopted;
    }

    public List<DocumentReference> getAdoptionPosts() {
        return adoptionPosts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDonationsMade(List<Donation> donationsMade) {
        this.donationsMade = donationsMade;
    }

    public int getMoneyRemaining() {
        return moneyRemaining;
    }

    public void setMoneyRemaining(int moneyRemaining) {
        this.moneyRemaining = moneyRemaining;
    }

    public void setAnimalsAdopted(List<DocumentReference> animalsAdopted) {
        this.animalsAdopted = animalsAdopted;
    }

    public void setAdoptionPosts(List<DocumentReference> adoptionPosts) {
        this.adoptionPosts = adoptionPosts;
    }
    public String getCountry() {
        return country;
    }

    public String getGender(){
        return gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", uid='" + uid + '\'' +
                ", country'" + country + '\'' +
                ", donationsMade=" + donationsMade +
                ", animalsAdopted=" + animalsAdopted +
                ", adoptionPosts=" + adoptionPosts +
                '}';
    }
}
