package com.example.acrigooo.Model;

import com.google.firebase.database.Exclude;

public class ImageModel {


    private String imageurl;
    private String key;




    public ImageModel() {
        //empty constructor needed
    }


    public String getImageUrl() {
        return imageurl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageurl = imageUrl;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }


}
