package com.othman.go4lunch.controllers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.facebook.login.Login;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.othman.go4lunch.R;
import com.othman.go4lunch.utils.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsActivity extends AppCompatActivity {


    @BindView(R.id.notifications_switch)
    SwitchCompat notificationsSwitch;
    @BindView(R.id.delete_button)
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);

        setDeleteButton();

    }


    private void setDeleteButton() {

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteUser();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });
    }


    // Delete user from Firebase and Firestore when delete button is clicked
    private void deleteUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            AuthUI.getInstance().delete(this).addOnSuccessListener(this, updateUIAfterRESTRequestsCompleted());

            UserHelper.deleteUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted() {

        return aVoid -> {
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(intent);
        };
    }
}