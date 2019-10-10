package com.othman.go4lunch.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;

public class RestaurantHelper {



    // COLLECTION REFERENCE
    public static CollectionReference getRestaurantsCollection() {
        return FirebaseFirestore.getInstance().collection("restaurants");
    }

    // CREATE
    public static Task<Void> createLikedRestaurant(Restaurant restaurant, String userId) {

        Restaurant toCreate = new Restaurant();
        return UserHelper.getUsersCollection().document(userId)
                .collection("restaurants").document(restaurant.getPlaceId()).set(toCreate);
    }


    // GET
    public static Task<QuerySnapshot> getAllRestaurantsForUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).collection("restaurants").get();
    }


    // UPDATE
    public static Task<Void> updateLike(Restaurant restaurant, User currentUser) {

        return RestaurantHelper.getRestaurantsCollection().document(restaurant.getPlaceId()).update("isLiked", true);
    }


    // DELETE
    public static Task<Void> deleteLikedRestaurant(Restaurant restaurant, String userId) {

        return UserHelper.getUsersCollection().document(userId).collection("restaurants")
                .document(restaurant.getPlaceId()).delete();
    }



}
