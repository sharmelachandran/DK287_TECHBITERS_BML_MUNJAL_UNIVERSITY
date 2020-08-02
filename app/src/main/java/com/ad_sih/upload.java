package com.ad_sih;

public class upload {
    private String Name;
    private String ImageUrl;

    public upload() {
        //empty constructor needed
    }

    public upload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        Name = name;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}