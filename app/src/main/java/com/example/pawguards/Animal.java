package com.example.pawguards;

public class Animal {
    private String name;
    private String age;
    private String type;
    private String description;

    public Animal(String name, String description, String age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
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

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
