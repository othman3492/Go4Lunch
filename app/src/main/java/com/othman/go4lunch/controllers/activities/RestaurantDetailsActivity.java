package com.othman.go4lunch.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.othman.go4lunch.BuildConfig;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.GooglePlaces;
import com.othman.go4lunch.models.GooglePlacesDetails;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.Workmate;
import com.othman.go4lunch.utils.GoogleAPIStreams;
import com.othman.go4lunch.views.WorkmatesAdapter;

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
    TextView restaurantTypeAndAddress;
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

        executePlacesDetailsRequest();

        workmateList = new ArrayList<>();
        addWorkmates();
        configureRecyclerView();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    // Fill activity with restaurant data
    private void updateData(GooglePlacesDetails.Result result) {






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


    // Execute HTTP request to retrieve details from a place
    private void executePlacesDetailsRequest() {

        String key = BuildConfig.google_apikey;
        String placeId = getIntent().getStringExtra("ID");

        this.disposable = GoogleAPIStreams.streamFetchPlacesDetails(key, placeId).subscribeWith(
                new DisposableObserver<GooglePlacesDetails>() {

                    @Override
                    public void onNext(GooglePlacesDetails googlePlacesDetails) {

                        updateData(googlePlacesDetails.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



}
