package com.example.pawguards;

public class Animal {
    private String name;
    private int age;
    private String type;
    private String description;
    private String gender;



    public Animal(String name, String description, int age, String type, String gender) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.description = description;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
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

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                "gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
