package com.othman.go4lunch.controllers.fragments;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.controllers.activities.RestaurantDetailsActivity;
import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.utils.GoogleAPIStreams;
import com.othman.go4lunch.utils.UserHelper;
import com.othman.go4lunch.views.RestaurantsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements RestaurantsAdapter.RecyclerViewOnClickListener {


    @BindView(R.id.restaurant_distance)
    TextView restaurantDistance;


    private List<Restaurant> restaurantList;
    private List<User> workmatesList;
    private RestaurantsAdapter adapter;
    private Disposable disposable;
    private static double currentLatitude;
    private static double currentLongitude;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_restaurants_list, container, false);

        restaurantList = new ArrayList<>();
        workmatesList = new ArrayList<>();
        setParameters(currentLatitude, currentLongitude);

        executeSearchNearbyPlacesRequest(v);

        //configureRecyclerView(v);

        return v;
    }


    // Configure RecyclerView to display articles
    private void configureRecyclerView(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.restaurants_recycler_view);
        this.adapter = new RestaurantsAdapter(this.restaurantList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    // Create a list of Restaurant objects from the API request results
    private void createRestaurantList(List<GooglePlaces.Result> results, View v) {

        List<Restaurant> restaurantList = new ArrayList<>();

        for (GooglePlaces.Result result : results) {

            Restaurant restaurant = new Restaurant().createRestaurantfromAPIResults(result);

            executePlacesDetailsRequest(restaurant, restaurant.getPlaceId());

            restaurant.setDistance(configureDistance(restaurant));

            configureWorkmatesNumber(restaurant);
            restaurant.setNbWorkmates(workmatesList.size());

            restaurantList.add(restaurant);
        }

        configureRecyclerView(v);
        updateRestaurantList(restaurantList);

    }


    // Fill the Restaurant list displayed in the RecyclerView with data from the API request
    private void updateRestaurantList(List<Restaurant> restaurantList) {

        this.restaurantList.addAll(restaurantList);
        this.adapter.notifyDataSetChanged();
    }


    // Get location parameters from MapFragment to use them in the request
    public static void setParameters(double latitude, double longitude) {

        currentLatitude = latitude;
        currentLongitude = longitude;
    }


    // Calculate distance between user location and restaurant location
    private int configureDistance(Restaurant restaurant) {

        Location currentLocation = new Location ("CURRENT LOCATION");
        Location restaurantLocation = new Location("RESTAURANT LOCATION");

        currentLocation.setLatitude(currentLatitude);
        currentLocation.setLongitude(currentLongitude);

        restaurantLocation.setLatitude(restaurant.getLatitude());
        restaurantLocation.setLongitude(restaurant.getLongitude());

        return (int) currentLocation.distanceTo(restaurantLocation);
    }

    // Get number of users who have chosen the restaurant
    private void configureWorkmatesNumber(Restaurant restaurant) {

        UserHelper.getAllUsers().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    User createUser = document.toObject(User.class);

                    if (!createUser.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) &&
                            createUser.getChosenRestaurant() != null
                            && createUser.getChosenRestaurant().getPlaceId().equals(restaurant.getPlaceId())) {
                        workmatesList.add(createUser);
                    }
                }
            }
        });
    }


    // Execute HTTP request to retrieve nearby places
    private void executeSearchNearbyPlacesRequest(View v) {

        String type = "restaurant";
        String location = currentLatitude + "," + currentLongitude;
        String key = BuildConfig.google_apikey;

        this.disposable = GoogleAPIStreams.streamFetchPlaces(key, type, location, 5000).subscribeWith(
                new DisposableObserver<GooglePlaces>() {
                    @Override
                    public void onNext(GooglePlaces googlePlaces) {

                        createRestaurantList(googlePlaces.getResults(), v);

                        Log.e("TAG", "On Next");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("TAG", "On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {

                        Log.e("TAG", "On Complete");
                    }
                });
    }


    // Execute HTTP request to retrieve more information from the place
    private void executePlacesDetailsRequest(Restaurant restaurant, String placeId) {

        String key = BuildConfig.google_apikey;

        this.disposable = GoogleAPIStreams.streamFetchPlacesDetails(key, placeId).subscribeWith(
                new DisposableObserver<GooglePlacesDetails>() {

                    @Override
                    public void onNext(GooglePlacesDetails googlePlacesDetails) {



                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    // Start RestaurantDetailsActivity when a RecyclerView item is clicked and get data
    @Override
    public void recyclerViewOnClick(int position) {

        Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
        Restaurant restaurant = restaurantList.get(position);
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
    }
}
