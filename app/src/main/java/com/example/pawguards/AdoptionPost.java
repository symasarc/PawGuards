package com.example.pawguards;

public class AdoptionPost {

    private String image;
    private String title;
    private String description;
    private String location;
    private Animal animal;

    public AdoptionPost(Animal anim) {
        this.image = anim.getAnimalPic();
        this.title = anim.getTitle();
        this.description = anim.getDescription();
        this.location = anim.getLocation();
        this.animal = anim;
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

    public String getImage() {
        return image;
    }

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

    public void setImage(String image) {
        this.image = image;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    @Override
    public String toString() {
        return "AdoptionPost{" +
//              "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", animal=" + animal +
                '}';
    }

}
