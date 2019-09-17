package com.othman.go4lunch.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.othman.go4lunch.R;
import com.othman.go4lunch.controllers.fragments.ListFragment;
import com.othman.go4lunch.controllers.fragments.MapFragment;
import com.othman.go4lunch.controllers.fragments.WorkmatesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.activity_main_page_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_page_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.activity_main_page_nav_view) NavigationView navigationView;
    @BindView(R.id.activity_main_page_bottom_nav_view)
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);

        // Configure UI
        setMapFragmentOnStartActivity();
        navigationView.setNavigationItemSelectedListener(this);
        configureDrawerLayout();
        configureBottomNavigationView();
    }

    // Display map by default when app starts
    private void setMapFragmentOnStartActivity() {

        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
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

                case R.id.bottom_map :
                    selectedFragment = new MapFragment();
                    break;
                case R.id.bottom_list_view :
                    selectedFragment = new ListFragment();
                    break;
                case R.id.bottom_workmates :
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

            case R.id.main_page_drawer_lunch :
            break;
            case R.id.main_page_drawer_settings :
            break;
            case R.id.main_page_drawer_logout :
                signOutFromFirebase();
            break;
        }


        return false;
    }


    private void signOutFromFirebase() {

        AuthUI.getInstance().signOut(this).addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());
    }


    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted() {

        return aVoid -> finish();
    }
}
