package com.othman.go4lunch.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.models.Workmate;
import com.othman.go4lunch.utils.GoogleAPIStreams;
import com.othman.go4lunch.utils.UserHelper;
import com.othman.go4lunch.views.WorkmatesAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantDetailsActivity extends AppCompatActivity {


    @BindView(R.id.restaurant_details_name)
    TextView restaurantName;
    @BindView(R.id.restaurant_details_type_address)
    TextView restaurantAddress;
    @BindView(R.id.restaurant_details_image)
    ImageView restaurantImage;
    @BindView(R.id.check_floating_action_button)
    FloatingActionButton checkFloatingActionButton;
    @BindView(R.id.uncheck_floating_action_button)
    FloatingActionButton uncheckFloatingActionButton;
    @BindView(R.id.call_constraint_layout)
    View callButton;
    @BindView(R.id.restaurant_details_call_button)
    ImageView callButtonIcon;
    @BindView(R.id.like_constraint_layout)
    View likeButton;
    @BindView(R.id.restaurant_details_like_button)
    ImageView likeButtonIcon;
    @BindView(R.id.website_constraint_layout)
    View websiteButton;
    @BindView(R.id.restaurant_details_website_button)
    ImageView websiteButtonIcon;
    @BindView(R.id.restaurant_details_star_1)
    ImageView restaurantStar1;
    @BindView(R.id.restaurant_details_star_2)
    ImageView restaurantStar2;
    @BindView(R.id.restaurant_details_star_3)
    ImageView restaurantStar3;


    private List<Workmate> workmateList;
    private WorkmatesAdapter adapter;
    private Disposable disposable;
    private boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        setIconColors();
        updateData();

        workmateList = new ArrayList<>();
        addWorkmates();
        configureRecyclerView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    // Update activity with restaurant data
    private void updateData() {

        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("RESTAURANT");

        restaurantName.setText(restaurant.getName());
        restaurantAddress.setText(restaurant.getAddress());

        Picasso.get().load(restaurant.getImageUrl()).into(restaurantImage);

        // Display stars depending on restaurant's rating
        if (restaurant.getRating() < 5.0 / 4.0 * 3.0)
            restaurantStar3.setVisibility(View.GONE);
        else if (restaurant.getRating() < 5.0 / 4.0 * 2.0)
            restaurantStar2.setVisibility(View.GONE);
        else if (restaurant.getRating() < 5.0 / 4.0)
            restaurantStar1.setVisibility(View.GONE);

        // Set buttons
        setFloatingActionButton(restaurant.getPlaceId());
        setCallButton(restaurant);
        setWebsiteButton(restaurant);

    }


    // Set floating action buttons
    private void setFloatingActionButton(String restaurant) {

        // Verify if restaurant is currently chosen, then set buttons state accordingly
        UserHelper.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User currentUser = documentSnapshot.toObject(User.class);
                if (currentUser.getRestaurant() != null
                        && currentUser.getRestaurant().equals(restaurant)) {

                    checkFloatingActionButton.hide();
                    uncheckFloatingActionButton.show();
                } else {
                    uncheckFloatingActionButton.hide();
                    checkFloatingActionButton.show();
                }
            }
        });


        // Add chosen restaurant to database and switch buttons
        checkFloatingActionButton.setOnClickListener(v -> {

            UserHelper.updateChosenRestaurant(FirebaseAuth.getInstance().getCurrentUser().getUid(), restaurant)
                    .addOnSuccessListener(aVoid -> {

                        checkFloatingActionButton.hide();
                        uncheckFloatingActionButton.show();
                        isChecked = true;
                    });
        });

        // Remove restaurant from database and switch buttons
        uncheckFloatingActionButton.setOnClickListener(v -> {


            UserHelper.updateChosenRestaurant(FirebaseAuth.getInstance().getCurrentUser().getUid(), null)
                    .addOnSuccessListener(aVoid -> {

                        uncheckFloatingActionButton.hide();
                        checkFloatingActionButton.show();
                        isChecked = false;
                    });
        });

    }


                // Set call button
            private void setCallButton(Restaurant restaurant) {

                callButton.setOnClickListener(v -> {

                    if (restaurant.getPhoneNumber() != null) {

                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + restaurant.getPhoneNumber()));
                        startActivity(callIntent);
                    } else {

                        Toast.makeText(v.getContext(), "There's no phone number found for this restaurant", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            // Set website button
            private void setWebsiteButton(Restaurant restaurant) {

                websiteButton.setOnClickListener(v -> {

                    if (restaurant.getWebsite() != null) {

                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                        startActivity(webIntent);
                    } else {

                        Toast.makeText(v.getContext(), "There's no website found for this restaurant", Toast.LENGTH_SHORT).show();
                    }


                });
            }


            // Configure RecyclerView to display articles
            private void configureRecyclerView() {

                RecyclerView recyclerView = findViewById(R.id.restaurant_details_recycler_view);
                this.adapter = new WorkmatesAdapter(this.workmateList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }


            private List<Workmate> addWorkmates() {

                workmateList.add(new Workmate());
                workmateList.add(new Workmate());
                workmateList.add(new Workmate());
                workmateList.add(new Workmate());
                workmateList.add(new Workmate());

                return workmateList;

            }


            // Dispose subscription
            private void disposeWhenDestroy() {
                if (this.disposable != null && !this.disposable.isDisposed())
                    this.disposable.dispose();
            }


            // Set icon colors programmatically
            private void setIconColors() {

                restaurantStar1.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.MULTIPLY);
                restaurantStar2.setColorFilter(ContextCompat.getColor(this, R.color.white));
                restaurantStar3.setColorFilter(ContextCompat.getColor(this, R.color.white));
                callButtonIcon.setColorFilter(ContextCompat.getColor(this, R.color.go4LunchThemeColor));
                likeButtonIcon.setColorFilter(ContextCompat.getColor(this, R.color.go4LunchThemeColor));
                websiteButtonIcon.setColorFilter(ContextCompat.getColor(this, R.color.go4LunchThemeColor));

            }


        }
