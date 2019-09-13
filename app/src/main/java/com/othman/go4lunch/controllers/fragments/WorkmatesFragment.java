package com.othman.go4lunch.controllers.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.othman.go4lunch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkmatesFragment extends Fragment {


    public WorkmatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workmates_list, container, false);
    }

}
