package com.othman.go4lunch.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.Workmate;
import com.othman.go4lunch.utils.GoogleAPIStreams;
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
    @BindView(R.id.call_constraint_layout)
    View callButton;
    @BindView(R.id.like_constraint_layout)
    View likeButton;
    @BindView(R.id.website_constraint_layout)
    View websiteButton;
    @BindView(R.id.restaurant_details_star_1)
    ImageView restaurantStar1;
    @BindView(R.id.restaurant_details_star_2)
    ImageView restaurantStar2;
    @BindView(R.id.restaurant_details_star_3)
    ImageView restaurantStar3;



    private List<Workmate> workmateList;
    private WorkmatesAdapter adapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        updateData();

        workmateList = new ArrayList<>();
        addWorkmates();
        configureRecyclerView();
    }


    @Override
    public void onDestroy(){
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
        setCallButton(restaurant);
        setWebsiteButton(restaurant);

    }


    // Set call button
    private void setCallButton(Restaurant restaurant) {

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (restaurant.getPhoneNumber() != null) {

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + restaurant.getPhoneNumber()));
                    startActivity(callIntent);
                } else {

                    Toast.makeText(v.getContext(), "There's no phone number found for this restaurant", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Set website button
    private void setWebsiteButton(Restaurant restaurant) {

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (restaurant.getWebsite() != null) {

                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                    startActivity(webIntent);
                } else {

                    Toast.makeText(v.getContext(), "There's no website found for this restaurant", Toast.LENGTH_SHORT).show();
                }


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
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



}
