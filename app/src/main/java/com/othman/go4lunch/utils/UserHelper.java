package com.othman.go4lunch.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;

public class UserHelper {



    // COLLECTION REFERENCE
    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection("users");
    }

    // CREATE
    public static Task<Void> createUser(String uid, String username, String urlPicture) {

        User userToCreate = new User(uid, username, urlPicture);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }


    // GET
    public static Task<DocumentSnapshot> getUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).get();
    }

    public static Task<QuerySnapshot> getAllUsers() {

        return UserHelper.getUsersCollection().get();
    }

    // UPDATE
    public static Task<Void> updateUsername(String username, String uid) {

        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updateChosenRestaurant(String uid, Restaurant restaurant) {

        return UserHelper.getUsersCollection().document(uid).update("restaurant", restaurant);
    }

    public static Task<Void> updateNotificationChoice(String uid, boolean isEnabled) {

        return UserHelper.getUsersCollection().document(uid).update("notificationsEnabled", isEnabled);
    }


    // DELETE
    public static Task<Void> deleteUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).delete();
    }



}
