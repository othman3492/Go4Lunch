package com.othman.go4lunch.models;


import com.othman.go4lunch.BuildConfig;

public class Restaurant {

    private String name;
    private String type;
    private String address;
    private double rating;
    private int radius;
    private String phoneNumber;
    private String website;
    private boolean isOpenNow;
    private String imageUrl;
    private String placeId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Restaurant() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public boolean isOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(boolean openNow) {
        isOpenNow = openNow;
    }


    // Create a Restaurant object and fill it with data from the result object
    public Restaurant createRestaurantfromAPIResults(GooglePlaces.Result result) {

        String imageBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&maxheight=75&photoreference=";

        Restaurant restaurant = new Restaurant();

        restaurant.name = result.getName();
        restaurant.type = result.getTypes().get(0);
        restaurant.address = result.getVicinity();
        restaurant.placeId = result.getPlaceId();

        if (result.getOpeningHours() != null)
            restaurant.isOpenNow = result.getOpeningHours().getOpenNow();

        if (result.getPhotos() != null)
            restaurant.imageUrl = imageBaseUrl + result.getPhotos().get(0).getPhotoReference()
                    + "&key=" + BuildConfig.google_apikey;


        return restaurant;
    }
}
