package com.othman.go4lunch.models;


import android.location.Location;

import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.utils.GoogleAPIStreams;

import java.io.Serializable;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class Restaurant implements Serializable {

    private String name;
    private String type;
    private String address;
    private double rating;
    private double latitude;
    private double longitude;
    private int distance;
    private String phoneNumber;
    private String website;
    private String isOpenNow;
    private String imageUrl;
    private String placeId;
    private List<User> usersList;

    private Disposable disposable;

    public Restaurant() {

    }


    public Restaurant(String id) {

        this.placeId = id;
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

    public String getOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(String openNow) {
        isOpenNow = openNow;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getDistance() { return distance; }

    public void setDistance(int distance) { this.distance = distance; }

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

    public String getIsOpenNow() {
        return isOpenNow;
    }

    public void setIsOpenNow(String isOpenNow) {
        this.isOpenNow = isOpenNow;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    // Create a Restaurant object and fill it with data from Search Results and Details Results
    public Restaurant createRestaurantfromAPIResults(GooglePlaces.Result result) {

        String imageBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&maxheight=100&photoreference=";

        Restaurant restaurant = new Restaurant();

        restaurant.name = result.getName();
        restaurant.type = result.getTypes().get(0);
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


        //executePlacesDetailsRequest(restaurant, restaurant.placeId);


        return restaurant;
    }


    // Execute HTTP request to retrieve more information from the place
    private void executePlacesDetailsRequest(Restaurant restaurant, String placeId) {

        String key = BuildConfig.google_apikey;

        this.disposable = GoogleAPIStreams.streamFetchPlacesDetails(key, placeId).subscribeWith(
                new DisposableObserver<GooglePlacesDetails>() {

                    @Override
                    public void onNext(GooglePlacesDetails googlePlacesDetails) {

                        addDataFromDetailsRequest(restaurant, googlePlacesDetails.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    // Complete restaurant object with data from Details Request
    private void addDataFromDetailsRequest(Restaurant restaurant, GooglePlacesDetails.Result result) {

        restaurant.rating = result.getRating();
        restaurant.phoneNumber = result.getFormattedPhoneNumber();
        restaurant.website = result.getWebsite();
    }
}
