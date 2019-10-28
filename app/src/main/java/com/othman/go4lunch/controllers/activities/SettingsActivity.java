package com.othman.go4lunch.controllers.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.utils.NotificationReceiver;
import com.othman.go4lunch.utils.UserHelper;

import java.util.Calendar;
import java.util.Objects;

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

        setNotificationsSwitch();
        setDeleteButton();

    }


    private void setNotificationsSwitch() {

        // Get switch state from Firestore
        UserHelper.getUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addOnSuccessListener(documentSnapshot -> {

                    User currentUser = documentSnapshot.toObject(User.class);
                    if (Objects.requireNonNull(currentUser).isNotificationsEnabled()) {

                        notificationsSwitch.setChecked(true);
                    }
                });

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                UserHelper.updateNotificationChoice(FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                configureNotificationAlarmManager();
            } else {

                UserHelper.updateNotificationChoice(FirebaseAuth.getInstance().getCurrentUser().getUid(), false);
                cancelNotificationAlarmManager();
            }
        });
    }


    // Create the AlarmManager to plan the notification to be called every day
    private void configureNotificationAlarmManager() {


        // Set the alarm to start at noon
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);

        Intent intent = new Intent(SettingsActivity.this, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }


    private void cancelNotificationAlarmManager() {

        Intent intent = new Intent(SettingsActivity.this, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


    private void setDeleteButton() {

        deleteButton.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteUser())
                .setNegativeButton(R.string.no, null)
                .show());
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