package com.othman.go4lunch.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;

public class RestaurantHelper {



    private static final String COLLECTION_NAME = "restaurants";




    // CREATE
    public static Task<DocumentReference> createLikedRestaurant(String uid) {

        Restaurant restaurantToCreate = new Restaurant();

        return UserHelper.getUsersCollection().document(uid).collection(COLLECTION_NAME).add(restaurantToCreate);
    }


    // GET
    public static Query getAllRestaurantsForUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).collection(COLLECTION_NAME).limit(50);
    }


    // DELETE
    public static Task<Void> deleteLikedRestaurant(Restaurant restaurant, String userId) {

        return UserHelper.getUsersCollection().document(userId).collection(COLLECTION_NAME)
                .document(restaurant.getPlaceId()).delete();
    }



}
