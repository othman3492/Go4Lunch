package com.othman.go4lunch.controllers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.othman.go4lunch.R;
import com.othman.go4lunch.utils.UserHelper;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    // Identifier for sign-in activity
    public static final int RC_SIGN_IN = 100;

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
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }

    // Launch Google sign-in activity
    private void startGoogleSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }

    // Launch Facebook sign-in activity
    private void startFacebookSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), RC_SIGN_IN);

    }

    // Launch Twitter sign-in activity
    private void startTwitterSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
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
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(constraintLayout, getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(constraintLayout, getString(R.string.error_unknown_error));
                }
            }
        }
    }


    // Get data from current user
    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean isUserLogged() {
        return (this.getCurrentUser() != null);
    }


    // Create user in Firestore and store data
    private void createUserInFirestore(){

        if (this.getCurrentUser() != null){

            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
            String username = this.getCurrentUser().getDisplayName();
            String uid = this.getCurrentUser().getUid();

            UserHelper.createUser(uid, username, urlPicture);
        }
    }
}









