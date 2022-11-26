package com.practice.practiceorderup01;

import java.io.Serializable;

public class Icons implements Serializable {

    private String name; //stores the name of the icon
    private int image; //stores the id of the image

    public Icons(){
    }

    public Icons(String name, int image){
        this.name = name;
        this.image = image;
    }

    //returns the name of the image
    public String getName(){
        return this.name;
    }

    //set the name of the icon
    public void setName(String name){
        this.name = name;
    }

    //get the image id
    public int getImage(){
        return this.image;
    }

    //set the image id
    public void setImage(int image){
        this.image = image;
    }
}
