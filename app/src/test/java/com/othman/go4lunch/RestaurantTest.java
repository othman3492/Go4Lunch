package com.othman.go4lunch;

import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.Restaurant;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTest {


    private String name;
    private String address;
    private double rating;
    private double latitude;
    private double longitude;
    private String isOpenNow;
    private String imageUrl;
    private String placeId;
    private String phoneNumber;
    private String website;

    private Restaurant restaurant;



    @Before
    public void setUp() {

        name = "Le Poisson Rouge";
        address = "32 Rue Paul Riquet, Frontignan";
        rating = 4.2;
        latitude = 43.429554;
        longitude = 3.765328999999999;
        isOpenNow = "true";
        imageUrl = "CmRaAAAAI3G4TYPI2ixK_EXsrPpT5i2NzFJSiFQT2FGzoOX014RQlG4fWPxFAhy9CrbglFGyXQAKHNWtBakbCSITy6aUnV7RsEqccBe0mrfCxd0OfpROVsB34VQP96Xao4cSY92wEhBiPQBbhPc55kBFd4Lk3LALGhSoLUjNRK-jylieOdSEt4uG33bi1w";
        placeId = "ChIJq3LbhGe1thIRhg2RiAHFif0";
        phoneNumber = "0499040553";
        website = "www.le-poisson-rouge.fr";

        restaurant = new Restaurant();

    }


    @Test
    public void createRestaurantWithSuccess() {

        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setRating(rating);
        restaurant.setLatitude(latitude);
        restaurant.setLongitude(longitude);
        restaurant.setOpenNow(isOpenNow);
        restaurant.setImageUrl(imageUrl);
        restaurant.setPlaceId(placeId);
        restaurant.setPhoneNumber(phoneNumber);
        restaurant.setWebsite(website);

        GooglePlaces.Result result = new GooglePlaces.Result();
        GooglePlacesDetails.Result detailsResult = new GooglePlacesDetails.Result();

        result.setName("Le Poisson Rouge");
        result.setVicinity("32 Rue Paul Riquet, Frontignan");
        result.setPlaceId("ChIJq3LbhGe1thIRhg2RiAHFif0");

        // Latitude / Longitude
        GooglePlaces.Geometry geometry = new GooglePlaces.Geometry();
        GooglePlaces.Location location = new GooglePlaces.Location();
        location.setLat(43.429554);
        location.setLng(3.765328999999999);
        geometry.setLocation(location);
        result.setGeometry(geometry);

        // Is Open Now
        GooglePlaces.OpeningHours openingHours = new GooglePlaces.OpeningHours();
        openingHours.setOpenNow(true);
        result.setOpeningHours(openingHours);

        // Image URL
        List<GooglePlaces.Photo> photos = new ArrayList<>();
        GooglePlaces.Photo photo = new GooglePlaces.Photo();
        photo.setPhotoReference("CmRaAAAAI3G4TYPI2ixK_EXsrPpT5i2NzFJSiFQT2FGzoOX014RQlG4fWPxFAhy9CrbglFGyXQAKHNWtBakbCSITy6aUnV7RsEqccBe0mrfCxd0OfpROVsB34VQP96Xao4cSY92wEhBiPQBbhPc55kBFd4Lk3LALGhSoLUjNRK");
        photos.add(photo);
        result.setPhotos(photos);

        detailsResult.setFormattedPhoneNumber("0499040553");
        detailsResult.setWebsite("www.le-poisson-rouge.fr");


        Restaurant createRestaurant = restaurant.createRestaurantfromAPIResults(result);

        assertEquals(restaurant.getName(), createRestaurant.getName());

    }

}
