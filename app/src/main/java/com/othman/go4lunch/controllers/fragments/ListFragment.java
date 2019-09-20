package com.othman.go4lunch.controllers.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.othman.go4lunch.R;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.views.RestaurantsAdapter;
import com.othman.go4lunch.views.WorkmatesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements RestaurantsAdapter.RecyclerViewOnClickListener {


    private List<Restaurant> restaurantList;
    private RestaurantsAdapter adapter;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_restaurants_list, container, false);

        restaurantList = new ArrayList<>();

        addRestaurants();

        configureRecyclerView(v);

        return v;
    }


    // Configure RecyclerView to display articles
    private void configureRecyclerView(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.workmates_recycler_view);
        this.adapter = new RestaurantsAdapter(this.restaurantList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    private List<Restaurant> addRestaurants() {

        restaurantList.add(new Restaurant());
        restaurantList.add(new Restaurant());
        restaurantList.add(new Restaurant());
        restaurantList.add(new Restaurant());
        restaurantList.add(new Restaurant());
        restaurantList.add(new Restaurant());
        restaurantList.add(new Restaurant());

        return restaurantList;
    }



    @Override
    public void recyclerViewOnClick(int position) {

    }
}
