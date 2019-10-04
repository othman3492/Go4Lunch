package com.othman.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    private String userId;
    private String username;
    @Nullable private String urlPicture;
    private String restaurant;


    public User() {

    }


    public User(String userId, String username, String urlPicture) {

        this.userId = userId;
        this.username = username;
        this.urlPicture = urlPicture;
    }


    public String getRestaurant() { return restaurant; }

    public void setRestaurant(String restaurant) { this.restaurant = restaurant; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }


}
