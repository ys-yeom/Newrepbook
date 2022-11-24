package com.example.newrepbook;

public class Recipe {
    private String profile;
    private String name;
    private String id;

    public Recipe() {
    }

    public Recipe(String profile, String name, String id) {
        this.profile = profile;
        this.name = name;
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
