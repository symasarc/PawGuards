package com.example.pawguards;

public class AdoptionPost {

//    private String image;
    private String title;
    private String description;
    private String location;
    private Animal animal;
    private String availability;

    public AdoptionPost(String image, String title, String description, String location, Animal animal, String availability) {
 //       this.image = image;
        this.title = title;
        this.description = description;
        this.location = location;
        this.animal = animal;
        this.availability = availability;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

//    public String getImage() {
//        return image;
//    }

    public Animal getAnimal() {
        return animal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public void setImage(String image) {
//        this.image = image;
//    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }



    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "AdoptionPost{" +
//              "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", animal=" + animal +
                ", availability='" + availability + '\'' +
                '}';
    }

}
