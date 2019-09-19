package com.othman.go4lunch.utils;

import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.GooglePlacesSearch;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleAPIService {


    @GET("nearbysearch/json")
    Observable<GooglePlacesSearch> getPlaces(@Query("key") String key,
                                                          @Query("location") String location,
                                                          @Query("radius") int radius,
                                                          @Query("type") String type);

    Retrofit retrofitGooglePlaces = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    @GET("details/json")
    Observable<GooglePlacesDetails> getPlacesDetails(@Query("key") String key,
                                                     @Query("place_id") String placeId);

    Retrofit retrofitGooglePlacesDetails = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


}
