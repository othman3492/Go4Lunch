package com.othman.go4lunch.controllers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.controllers.fragments.ListFragment;
import com.othman.go4lunch.controllers.fragments.MapFragment;
import com.othman.go4lunch.controllers.fragments.WorkmatesFragment;
import com.othman.go4lunch.models.GooglePlacesSearch;
import com.othman.go4lunch.utils.GoogleAPIStreams;
import com.twitter.sdk.android.core.models.Search;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);

        // Configure UI
        updateUIWhenCreating();
        navigationView.setNavigationItemSelectedListener(this);
        configureDrawerLayout();
        configureBottomNavigationView();
    }

    // Get data from current user
    @Nullable
    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected boolean isUserLogged() {
        return (this.getCurrentUser() != null);
    }


    // Configure menu in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_page_menu, menu);

        // Configure the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_activity_main_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.main_page_drawer_lunch:
                break;
            case R.id.main_page_drawer_settings:
                break;
            case R.id.main_page_drawer_logout:
                signOutFromFirebase();
                break;
        }


        return false;
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

            // Get user profile picture from Firebase
            if (getCurrentUser().getPhotoUrl() != null) {

                Glide.with(this)
                        .load(getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(headerUserImage);
            }

            // Get username and email from Firebase, and update views
            String username = TextUtils.isEmpty(getCurrentUser().getDisplayName()) ?
                    getString(R.string.no_username_found) : this.getCurrentUser().getDisplayName();
            String email = TextUtils.isEmpty(getCurrentUser().getEmail()) ?
                    getString(R.string.no_email_found) : this.getCurrentUser().getEmail();

            headerUserName.setText(username);
            headerUserEmail.setText(email);
        }

    }


    // Log out user and update UI
    private void signOutFromFirebase() {

        AuthUI.getInstance().signOut(this).addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted() {

        return aVoid -> finish();
    }

}



