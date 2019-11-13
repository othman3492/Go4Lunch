package com.othman.go4lunch.models;


import java.util.List;

public class User {

    private String userId;
    private String username;
    private String urlPicture;
    private Restaurant chosenRestaurant;
    private List<Restaurant> likedRestaurants;
    private boolean notificationsEnabled;


    public User() {

    }


    public User(String userId, String username, String urlPicture, Restaurant restaurant,
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

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notifications) {
        this.notificationsEnabled = notifications;
    }
}
