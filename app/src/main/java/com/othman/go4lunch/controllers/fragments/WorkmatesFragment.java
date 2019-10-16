package com.othman.go4lunch.controllers.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.utils.UserHelper;
import com.othman.go4lunch.views.WorkmatesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkmatesFragment extends Fragment {


    private List<User> workmateList;
    private WorkmatesAdapter adapter;


    public WorkmatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_workmates_list, container, false);

        workmateList = new ArrayList<>();
        setWorkmatesList(v);


        return v;
    }


    // Configure RecyclerView to display articles
    private void configureRecyclerView(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.workmates_recycler_view);
        this.adapter = new WorkmatesAdapter(this.workmateList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    private void setWorkmatesList(View v) {

        UserHelper.getAllUsers().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        User createUser = document.toObject(User.class);

                        if (!createUser.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        workmateList.add(createUser);
                    }

                    configureRecyclerView(v);
                }
            }
        });
    }
}
