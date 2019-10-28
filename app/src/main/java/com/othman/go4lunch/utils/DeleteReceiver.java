package com.othman.go4lunch.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.othman.go4lunch.models.User;

import java.util.Objects;


public class DeleteReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        UserHelper.getAllUsers().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                    User createUser = document.toObject(User.class);

                    UserHelper.updateChosenRestaurant(createUser.getUserId(), null);
                }
            }
        });
    }




    }

