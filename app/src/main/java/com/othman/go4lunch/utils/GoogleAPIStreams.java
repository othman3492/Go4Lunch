package com.othman.go4lunch.utils;


// Make HTTP requests on Google Places API

import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.GooglePlacesSearch;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface GoogleAPIStreams {


    static Observable<GooglePlacesSearch> streamFetchPlaces(String key, String location, int radius) {

        GoogleAPIService googleAPIService = GoogleAPIService.retrofitGooglePlaces.create(GoogleAPIService.class);

        return googleAPIService.getPlaces(key, location, radius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }


    static Observable<GooglePlacesDetails> streamFetchPlacesDetails(String key, String placeId) {

        GoogleAPIService googleAPIService = GoogleAPIService.retrofitGooglePlacesDetails.create(GoogleAPIService.class);

        return googleAPIService.getPlacesDetails(key, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }



}
