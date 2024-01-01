package com.example.pawguards;

import java.util.List;

public class User {
    private String name;
    private String surname;
    private String email;
    //private String profilePicture;
    private String uid;
    private List<Donation> donationsMade;
    private List<Animal> animalsAdopted;
    private List<AdoptionPost> adoptionPosts;
    private String country;
    private String gender;
    private String profilePicture;

    public User() {
    }


    public User(String profilePicture, String name, String surname, String email, String uid, List<Donation> donationsMade, List<Animal> animalsAdopted, List<AdoptionPost> adoptionPosts, String country, String gender) {
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

    public List<Animal> getAnimalsAdopted() {
        return animalsAdopted;
    }

    public List<AdoptionPost> getAdoptionPosts() {
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

    public void setAnimalsAdopted(List<Animal> animalsAdopted) {
        this.animalsAdopted = animalsAdopted;
    }

    public void setAdoptionPosts(List<AdoptionPost> adoptionPosts) {
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
