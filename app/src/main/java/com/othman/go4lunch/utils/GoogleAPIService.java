package com.othman.go4lunch.utils;

import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.GooglePlacesDetails;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface GoogleAPIService {


    @GET("nearbysearch/json?")
    Observable<GooglePlaces> getPlaces(@Query("location") String location,
                                       @Query("type") String type,
                                       @Query("radius") int radius,
                                       @Query("key") String key);

    Retrofit retrofitGooglePlaces = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    @GET("details/json?")
    Observable<GooglePlacesDetails> getPlacesDetails(@Query("key") String key,
                                                     @Query("place_id") String placeId);

    Retrofit retrofitGooglePlacesDetails = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


}
