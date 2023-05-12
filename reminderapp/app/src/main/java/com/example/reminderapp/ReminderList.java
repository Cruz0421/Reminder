/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: homepage that shows all active reminders
*/

package com.example.reminderapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReminderList extends AppCompatActivity implements View.OnClickListener{

    Button addReminder;
    LinearLayout layout;

    private static final String CHANNEL_ID = "CHANNEL_ID";

    // main functionality
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderlist);

        addReminder = (Button) findViewById(R.id.addreminder);
        addReminder.setOnClickListener(this);

        layout = findViewById(R.id.container);

        createNotificationChannel();
        Notification notif = new Notification(this);

        // TODO: for loop for each reminder
        String name = "Reminder1";
        String date = "11-5-2023";
        String time = "18:33";
        String repeat = "Hourly";
        addCard(name, date, time, repeat);
        notif.execute(name);

        String name2 = "Reminder2";
        String date2 = "11-5-2023";
        String time2 = "19:33";
        String repeat2 = "Hourly";
        addCard(name2, date2, time2, repeat2);
    }

    // creates a notification channel, which is needed to send notifications
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // hander for click events
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addreminder:
                Intent editReminder = new Intent(ReminderList.this, AddReminder.class);
                startActivity(editReminder);
                break;
        }
    }

    // draws a new card for a given reminder
    private void addCard(String name, String date, String time, String repeat) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        TextView dateView = view.findViewById(R.id.date);
        TextView timeView = view.findViewById(R.id.time);
        TextView repeatView = view.findViewById(R.id.repeat);

        Button edit = view.findViewById(R.id.delete);

        nameView.setText(name);
        dateView.setText(date);
        timeView.setText(time);
        repeatView.setText(repeat);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
                // TODO: delete from DB on click
            }
        });
        layout.addView(view);
    }
}