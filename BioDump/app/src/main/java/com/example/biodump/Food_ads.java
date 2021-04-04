package com.example.biodump;

public class Food_ads {
    private String Name;
    private String Location;
    private Integer Person;
    private String imageUrl;

    public Food_ads() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Integer getPerson() {
        return Person;
    }

    public void setPerson(Integer person) {
        Person = person;
    }

    public Food_ads(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
