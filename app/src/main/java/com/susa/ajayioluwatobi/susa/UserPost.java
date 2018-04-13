package com.susa.ajayioluwatobi.susa;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by ajayioluwatobi on 4/9/18.
 */

public class UserPost {
    public UserPost(int price, String address, String location, String post) {
        this.price = price;
        this.address = address;
        this.location = location;

        this.post_image = post;
    }

    public int price;
    public String address;
    public String location;

    public String post_image;


    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }









    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }






    public UserPost() {


    }
}
