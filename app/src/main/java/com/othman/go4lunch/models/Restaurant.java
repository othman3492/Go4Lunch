package com.othman.go4lunch.models;


import com.othman.go4lunch.BuildConfig;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String name;
    private String address;
    private double rating;
    private double latitude;
    private double longitude;
    private int distance;
    private int nbWorkmates;
    private String phoneNumber;
    private String website;
    private String isOpenNow;
    private String imageUrl;
    private String placeId;

    public Restaurant() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public int getDistance() { return distance; }

    public void setDistance(int distance) { this.distance = distance; }

    public int getNbWorkmates() { return nbWorkmates; }

    public void setNbWorkmates(int nbWorkmates) { this.nbWorkmates = nbWorkmates; }

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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIsOpenNow() {
        return isOpenNow;
    }

    public void setIsOpenNow(String isOpenNow) {
        this.isOpenNow = isOpenNow;
    }

    // Create a Restaurant object and fill it with data from Search Results and Details Results
    public Restaurant createRestaurantfromAPIResults(GooglePlaces.Result result) {

        String imageBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&maxheight=300&photoreference=";

        Restaurant restaurant = new Restaurant();

        restaurant.name = result.getName();
        restaurant.address = result.getVicinity();
        restaurant.placeId = result.getPlaceId();
        restaurant.latitude = result.getGeometry().getLocation().getLat();
        restaurant.longitude = result.getGeometry().getLocation().getLng();

        if (result.getOpeningHours() != null) {
            restaurant.isOpenNow = result.getOpeningHours().getOpenNow().toString();
        }

        if (result.getPhotos() != null)
            restaurant.imageUrl = imageBaseUrl + result.getPhotos().get(0).getPhotoReference()
                    + "&key=" + BuildConfig.google_apikey;


        return restaurant;
    }


    // Add data from Details Request to Restaurant object
    public void addDataFromDetailsRequest(Restaurant restaurant, GooglePlacesDetails.Result result) {

        restaurant.rating = result.getRating();
        restaurant.phoneNumber = result.getFormattedPhoneNumber();
        restaurant.website = result.getWebsite();
    }
}
