package com.othman.go4lunch.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;


public class DeleteReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        UserHelper.updateChosenRestaurant(FirebaseAuth.getInstance().getCurrentUser().getUid(), null);
    }




    }

