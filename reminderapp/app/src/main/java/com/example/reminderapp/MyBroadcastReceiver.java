/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: Needed to make notifications work
*/

package com.example.reminderapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyBroadcastReceiver extends BroadcastReceiver {

    static final String ACTION_SNOOZE = "OK";
    static final String EXTRA_NOTIFICATION_ID = "notification-id";

    private static final String TAG = "receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_SNOOZE.equals(intent.getAction())) {
            int notificationId = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID);
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationmanager.cancel(notificationId);
        }
    }
}