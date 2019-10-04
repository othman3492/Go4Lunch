package com.othman.go4lunch.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.othman.go4lunch.models.Restaurant;
import com.othman.go4lunch.models.User;

public class UserHelper {


    private static final String COLLECTION_NAME = "users";

    // COLLECTION REFERENCE
    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // CREATE USER
    public static Task<Void> createUser(String uid, String username, String urlPicture) {

        User userToCreate = new User(uid, username, urlPicture);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }


    // GET USER
    public static Task<DocumentSnapshot> getUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).get();
    }


    // UPDATE USER DATA
    public static Task<Void> updateUsername(String username, String uid) {

        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updateChosenRestaurant(String uid, String restaurant) {

        return UserHelper.getUsersCollection().document(uid).update("restaurant", restaurant);
    }


    // DELETE USER
    public static Task<Void> deleteUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).delete();
    }



}
