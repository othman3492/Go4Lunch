package com.othman.go4lunch.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.othman.go4lunch.R;
import com.othman.go4lunch.models.Workmate;
import com.othman.go4lunch.views.WorkmatesAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity {


    private List<Workmate> workmateList;
    private WorkmatesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        workmateList = new ArrayList<>();
        addWorkmates();
        configureRecyclerView();
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
}
