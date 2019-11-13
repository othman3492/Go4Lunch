package com.othman.go4lunch.controllers.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.controllers.activities.MainPageActivity;
import com.othman.go4lunch.controllers.activities.RestaurantDetailsActivity;
import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.utils.GoogleAPIStreams;
import com.othman.go4lunch.utils.UserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.google.android.gms.location.places.Places.getGeoDataClient;
import static com.google.android.gms.location.places.Places.getPlaceDetectionClient;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.map_view)
    MapView mapView;

    private GoogleMap googleMap;

    private FusedLocationProviderClient fusedLocationProviderClient;

    // Default location set to Paris if permission is not granted
    private final LatLng defaultLocation = new LatLng(48.8566, 2.3522);
    private static final int DEFAULT_ZOOM = 15;
    private static final int LOCATION_REQUEST_CODE = 101;
    private boolean locationPermissionGranted;

    // Last known location retrieved by the Fused Location Provider
    private Location lastKnownLocation;
    private double currentLatitude;
    private double currentLongitude;

    // Keys for storing fragment state
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Retrieve location and camera position from saved instance state
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            CameraPosition cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        GeoDataClient geoDataClient = getGeoDataClient(Objects.requireNonNull(getContext()));
        PlaceDetectionClient placeDetectionClient = getPlaceDetectionClient(getContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        // Ask for permission, get location and set position of the map, then execute Nearby Places request
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation(map);

    }


    // Get current location of the device and position the map's camera
    private void getDeviceLocation(GoogleMap map) {

        if (locationPermissionGranted) {

            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {

                if (task.isSuccessful()) {

                    // Set the map's camera
                    lastKnownLocation = task.getResult();
                    currentLatitude = Objects.requireNonNull(lastKnownLocation).getLatitude();
                    currentLongitude = lastKnownLocation.getLongitude();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(currentLatitude, currentLongitude), DEFAULT_ZOOM));

                    executeSearchNearbyPlacesRequest(map);
                }
            });

        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }


    // Ask user for permission to use the device location
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }


    // Check if permission is granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        locationPermissionGranted = false;

        // If request is cancelled, the result arrays are empty
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }


    // Update the map's UI settings if location permission is granted
    private void updateLocationUI() {

        if (googleMap == null) {
            return;
        }
        if (locationPermissionGranted) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            lastKnownLocation = null;
            getLocationPermission();
        }
    }


    // Execute HTTP request to retrieve nearby places
    private void executeSearchNearbyPlacesRequest(GoogleMap map) {

        String type = "restaurant";
        String location = currentLatitude + "," + currentLongitude;
        String key = BuildConfig.google_apikey;

        Disposable disposable = GoogleAPIStreams.streamFetchPlaces(key, type, location, 5000).subscribeWith(
                new DisposableObserver<GooglePlaces>() {
                    @Override
                    public void onNext(GooglePlaces googlePlaces) {

                        setMarkersOnMap(map, createRestaurantList(googlePlaces.getResults()));
                        MainPageActivity.setParameters(currentLatitude, currentLongitude);

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


    // Create a list of Restaurant objects from the API request results
    private List<Restaurant> createRestaurantList(List<GooglePlaces.Result> results) {

        List<Restaurant> restaurantList = new ArrayList<>();

        for (GooglePlaces.Result result : results) {

            Restaurant restaurant = new Restaurant().createRestaurantfromAPIResults(result);
            restaurantList.add(restaurant);
        }

        return restaurantList;

    }


    // Set markers on map for all restaurants and set info windows on click to open RestaurantDetailsActivity
    private void setMarkersOnMap(GoogleMap map, List<Restaurant> restaurantList) {

        for (Restaurant restaurant : restaurantList) {

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()))
                    .title(restaurant.getName()));

            // Change marker color to blue if restaurant is liked
            UserHelper.getUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .addOnSuccessListener(documentSnapshot -> {

                        User currentUser = documentSnapshot.toObject(User.class);
                        if (Objects.requireNonNull(currentUser).getLikedRestaurants() != null) {

                            for (Restaurant likedRestaurant : currentUser.getLikedRestaurants()) {
                                if (likedRestaurant.getPlaceId().equals(restaurant.getPlaceId())) {
                                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                }
                            }
                        }
                    });

            // Change marker color to green if another user has chosen the restaurant
            UserHelper.getAllUsers().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        User createUser = document.toObject(User.class);

                        if (!createUser.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) &&
                                createUser.getChosenRestaurant() != null &&
                                createUser.getChosenRestaurant().getPlaceId().equals(restaurant.getPlaceId())) {

                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }
                    }
                }
            });

            map.setOnInfoWindowClickListener(marker1 -> {

                LatLng latLng = marker1.getPosition();

                for (Restaurant restaurant1 : restaurantList) {

                    if (latLng.equals(new LatLng(restaurant1.getLatitude(), restaurant1.getLongitude()))) {

                        Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
                        intent.putExtra("RESTAURANT", restaurant1);
                        startActivity(intent);
                    }
                }
            });

        }
    }

    // Get current latitude and longitude to use them in ListFragment's request
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ListFragment.setParameters(currentLatitude, currentLongitude);
    }


}