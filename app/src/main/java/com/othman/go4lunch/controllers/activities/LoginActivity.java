package com.othman.go4lunch.controllers.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.utils.UserHelper;

import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    // Identifier for sign-in activity
    private static final int RC_SIGN_IN = 100;

    @BindView(R.id.main_constraint_layout)
    ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove toolbar on main screen
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }


    // Start sign-in activities when buttons clicked
    @OnClick(R.id.email_button)
    public void onClickEmailLoginButton() {
        startEmailSignInActivity();
    }

    @OnClick(R.id.google_button)
    public void onClickGoogleLoginButton() {
        startGoogleSignInActivity();
    }

    @OnClick(R.id.facebook_button)
    public void onClickFacebookLoginButton() {
        startFacebookSignInActivity();
    }

    @OnClick(R.id.twitter_button)
    public void onClickTwitterLoginButton() {
        startTwitterSignInActivity();
    }


    // Launch email sign-in activity
    private void startEmailSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }

    // Launch Google sign-in activity
    private void startGoogleSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }

    // Launch Facebook sign-in activity
    private void startFacebookSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }

    // Launch Twitter sign-in activity
    private void startTwitterSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.TwitterBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }


    // Create a snackbar
    private void showSnackbar(ConstraintLayout constraintLayout, String message) {

        Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT).show();
    }


    // Show message depending on result from sign-in
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Show message, create user and start MainPageActivity
                showSnackbar(constraintLayout, getString(R.string.connection_succeed));
                createUserInFirestore();
                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
            } else {
                if (response == null) {
                    showSnackbar(constraintLayout, getString(R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(constraintLayout, getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(constraintLayout, getString(R.string.error_unknown_error));
                }
            }
        }
    }


    // Create user in Firestore and store data
    private void createUserInFirestore() {

        String urlPicture = (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl() != null) ?
                FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString() : null;
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        UserHelper.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {

            User currentUser = documentSnapshot.toObject(User.class);
            if (currentUser != null)
                UserHelper.createUser(uid, username, urlPicture, currentUser.getChosenRestaurant(),
                        currentUser.getLikedRestaurants(), currentUser.isNotificationsEnabled());
            else
                UserHelper.createUser(uid, username, urlPicture, null, null, false);
        });


    }
}









