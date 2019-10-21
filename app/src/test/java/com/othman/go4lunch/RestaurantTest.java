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
    private double latitude;
    private double longitude;
    private String isOpenNow;
    private String imageUrl;
    private String placeId;

    private Restaurant restaurant;


    @Before
    public void setUp() {

        name = "Le Poisson Rouge";
        address = "32 Rue Paul Riquet, Frontignan";
        latitude = 43.429554;
        longitude = 3.765328999999999;
        isOpenNow = "true";
        imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&maxheight=300&photoreference=CmRaAAAAI3G4TYPI2ixK_EXsrPpT5i2NzFJSiFQT2FGzoOX014RQlG4fWPxFAhy9CrbglFGyXQAKHNWtBakbCSITy6aUnV7RsEqccBe0mrfCxd0OfpROVsB34VQP96Xao4cSY92wEhBiPQBbhPc55kBFd4Lk3LALGhSoLUjNRK&key=" + BuildConfig.google_apikey;
        placeId = "ChIJq3LbhGe1thIRhg2RiAHFif0";

        restaurant = new Restaurant();

    }


    @Test
    public void createRestaurantWithSuccess() {

        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setLatitude(latitude);
        restaurant.setLongitude(longitude);
        restaurant.setIsOpenNow(isOpenNow);
        restaurant.setImageUrl(imageUrl);
        restaurant.setPlaceId(placeId);

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



        Restaurant createRestaurant = restaurant.createRestaurantfromAPIResults(result);

        assertEquals(restaurant.getName(), createRestaurant.getName());
        assertEquals(restaurant.getAddress(), createRestaurant.getAddress());
        assertEquals(restaurant.getLatitude(), createRestaurant.getLatitude(), 0.1);
        assertEquals(restaurant.getLongitude(), createRestaurant.getLongitude(), 0.1);
        assertEquals(restaurant.getIsOpenNow(), createRestaurant.getIsOpenNow());
        assertEquals(restaurant.getImageUrl(), createRestaurant.getImageUrl());
        assertEquals(restaurant.getPlaceId(), createRestaurant.getPlaceId());

    }

}

