package com.othman.go4lunch.controllers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.controllers.fragments.ListFragment;
import com.othman.go4lunch.controllers.fragments.MapFragment;
import com.othman.go4lunch.controllers.fragments.WorkmatesFragment;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.utils.DeleteReceiver;
import com.othman.go4lunch.utils.UserHelper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.activity_main_page_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_page_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.activity_main_page_nav_view)
    NavigationView navigationView;
    @BindView(R.id.activity_main_page_bottom_nav_view)
    BottomNavigationView bottomNavigationView;

    private Disposable disposable;
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static double currentLatitude;
    private static double currentLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);

        // Initialize Places SDK for Autocomplete
        if (!Places.isInitialized())
            Places.initialize(this, BuildConfig.google_apikey);

        // Configure UI
        updateUIWhenCreating();
        navigationView.setNavigationItemSelectedListener(this);
        configureDrawerLayout();
        configureBottomNavigationView();

        // Delete all restaurants everyday
        configureDeleteAlarmManager();
    }

    // Get data from current user
    @Nullable
    private FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    // Configure menu in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_page_menu, menu);

        return true;
    }


    private void configureDrawerLayout() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    // Set a back button in the toolbar
    public void onBackPressed() {

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Configure Bottom Navigation View and display fragments
    private void configureBottomNavigationView() {

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {

                case R.id.bottom_map:
                    selectedFragment = new MapFragment();
                    break;
                case R.id.bottom_list_view:
                    selectedFragment = new ListFragment();
                    break;
                case R.id.bottom_workmates:
                    selectedFragment = new WorkmatesFragment();
            }

            // Display fragment depending on item selected
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(selectedFragment)).commit();

            return true;
        });
    }

    // Set search button to use Autocomplete
    public boolean onOptionsItemSelected(MenuItem item) {

        // Set list of needed data
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.TYPES, Place.Field.ADDRESS,
                Place.Field.RATING, Place.Field.PHONE_NUMBER, Place.Field.WEBSITE_URI, Place.Field.PHOTO_METADATAS);

        if (item.getItemId() == R.id.menu_activity_main_search) {
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .setLocationBias(RectangularBounds.newInstance(
                            new LatLng(currentLatitude - 0.1, currentLongitude - 0.1),
                            new LatLng(currentLatitude + 0.1, currentLongitude + 0.1)))
                    .setTypeFilter(TypeFilter.ESTABLISHMENT)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Get place from Autocomplete Intent and create Restaurant from place data
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = Autocomplete.getPlaceFromIntent(Objects.requireNonNull(data));

                Intent intent = new Intent(this, RestaurantDetailsActivity.class);
                intent.putExtra("RESTAURANT", createRestaurantFromPlaceAutocomplete(place));
                startActivity(intent);

            }

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

            Toast.makeText(this, R.string.error_unknown_error, Toast.LENGTH_SHORT).show();
        }
    }


    // Create Restaurant object from Autocomplete Place object
    private Restaurant createRestaurantFromPlaceAutocomplete(Place place) {

        String imageBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&maxheight=100&photoreference=";

        Restaurant restaurant = new Restaurant();

        restaurant.setPlaceId(place.getId());
        restaurant.setName(place.getName());
        restaurant.setAddress(place.getAddress());
        if (place.getWebsiteUri() != null) restaurant.setWebsite(place.getWebsiteUri().toString());
        restaurant.setPhoneNumber(place.getPhoneNumber());

        if (place.getPhotoMetadatas() != null)
            restaurant.setImageUrl(imageBaseUrl + place.getPhotoMetadatas().get(0).zza() + "&key=" + BuildConfig.google_apikey);

        return restaurant;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            // Open restaurant details
            case R.id.main_page_drawer_lunch:
                startChosenRestaurantDetailsActivity();
                break;
            // Open SettingsActivity
            case R.id.main_page_drawer_settings:
                Intent settingsIntent = new Intent(MainPageActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            // Sign out from app
            case R.id.main_page_drawer_logout:
                signOutFromFirebase();
                break;
        }


        return false;
    }


    // Start selected RestaurantDetailsActivity
    private void startChosenRestaurantDetailsActivity() {

        UserHelper.getUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addOnSuccessListener(documentSnapshot -> {

            User currentUser = documentSnapshot.toObject(User.class);

            Restaurant restaurant = Objects.requireNonNull(currentUser).getChosenRestaurant();

            if (restaurant != null) {
                Intent intent = new Intent(MainPageActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("RESTAURANT", restaurant);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.no_restaurant_selected), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Update UI when activity is creating
    private void updateUIWhenCreating() {

        // Display map fragment by default
        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Configure views if current user is not null
        if (getCurrentUser() != null) {

            View header = navigationView.getHeaderView(0);
            ImageView headerUserImage = header.findViewById(R.id.header_user_image);
            TextView headerUserName = header.findViewById(R.id.header_user_name);
            TextView headerUserEmail = header.findViewById(R.id.header_user_email);

            // Get user profile picture from Firebase, or set blank profile picture
            if (getCurrentUser().getPhotoUrl() != null) {

                Glide.with(this)
                        .load(getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(headerUserImage);
            } else {

                Glide.with(this)
                        .load(R.drawable.blank_profile)
                        .apply(RequestOptions.circleCropTransform())
                        .into(headerUserImage);
            }

            // Get data from Firebase, and update views
            UserHelper.getUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .addOnSuccessListener(documentSnapshot -> {

                        String username = TextUtils.isEmpty(getCurrentUser().getDisplayName()) ?
                                getString(R.string.no_username_found) : FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        String email = TextUtils.isEmpty(getCurrentUser().getEmail()) ?
                                getString(R.string.no_email_found) : FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        headerUserName.setText(username);
                        headerUserEmail.setText(email);

                    });
        }

    }


    // Log out user and update UI
    private void signOutFromFirebase() {

        AuthUI.getInstance().signOut(this).addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted() {

        return aVoid -> MainPageActivity.this.finish();
    }


    // Get location parameters from MapFragment to use them in the request
    public static void setParameters(double latitude, double longitude) {

        currentLatitude = latitude;
        currentLongitude = longitude;
    }


    // Create AlarmManager to plan the deleting of all chosen restaurants everyday at 4:00 PM
    private void configureDeleteAlarmManager() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);

        Intent intent = new Intent(MainPageActivity.this, DeleteReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}



