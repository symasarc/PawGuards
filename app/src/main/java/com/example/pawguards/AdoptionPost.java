package com.example.pawguards;

public class AdoptionPost {

    private User poster;
    private String title;
    private String description;
    private String location;
    private String image;
    private Animal animal;

    public AdoptionPost(User poster, String title, String description, String location, String image, Animal animal) {
        this.poster = poster;
        this.title = title;
        this.description = description;
        this.location = location;
        this.image = image;
        this.animal = animal;
    }

    public User getPoster() {
        return poster;
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

    public void setPoster(User poster) {
        this.poster = poster;
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
                "poster='" + poster + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", animal='" + animal + '\'' +
                '}';
    }

}
