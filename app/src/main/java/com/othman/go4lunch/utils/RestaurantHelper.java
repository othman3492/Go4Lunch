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


    private static final String COLLECTION_NAME = "restaurants";

    // COLLECTION REFERENCE
    public static CollectionReference getRestaurantsCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // CREATE RESTAURANT
    public static Task<Void> createRestaurant(String id) {

        Restaurant restaurantToCreate = new Restaurant(id);
        return RestaurantHelper.getRestaurantsCollection().document(id).set(restaurantToCreate);
    }


    // GET RESTAURANT
    public static Task<DocumentSnapshot> getRestaurant(String id) {

        return RestaurantHelper.getRestaurantsCollection().document(id).get();
    }

    public static Task<QuerySnapshot> getAllRestaurants() {

        return RestaurantHelper.getRestaurantsCollection().get();
    }


    // UPDATE RESTAURANT LIKES
    public static Task<Void> updateLike(Restaurant restaurant, User currentUser) {

        return RestaurantHelper.getRestaurantsCollection().document(restaurant.getPlaceId()).update("isLiked", true);
    }


    // DELETE RESTAURANT
    public static Task<Void> deleteRestaurant(String id) {

        return RestaurantHelper.getRestaurantsCollection().document(id).delete();
    }



}
