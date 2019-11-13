package com.othman.go4lunch.models;

import androidx.annotation.Nullable;

import java.util.List;

public class User {

    private String userId;
    private String username;
    @Nullable
    private String urlPicture;
    private Restaurant chosenRestaurant;
    private List<Restaurant> likedRestaurants;
    private boolean notificationsEnabled;


    public User() {

    }


    public User(String userId, String username, @org.jetbrains.annotations.Nullable String urlPicture, Restaurant restaurant,
                List<Restaurant> likedRestaurants, boolean isEnabled) {

        this.userId = userId;
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = restaurant;
        this.likedRestaurants = likedRestaurants;
        this.notificationsEnabled = isEnabled;
    }


    public Restaurant getChosenRestaurant() {
        return chosenRestaurant;
    }

    public void setChosenRestaurant(Restaurant chosenRestaurant) {
        this.chosenRestaurant = chosenRestaurant;
    }

    public List<Restaurant> getLikedRestaurants() {
        return likedRestaurants;
    }

    public void setLikedRestaurants(List<Restaurant> likedRestaurants) {
        this.likedRestaurants = likedRestaurants;
    }

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

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notifications) {
        this.notificationsEnabled = notifications;
    }
}
