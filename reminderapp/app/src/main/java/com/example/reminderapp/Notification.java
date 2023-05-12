/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: Creates and sends notifications
*/

package com.example.reminderapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class Notification extends AsyncTask<String, String, String> {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private Context ctx;
    private AtomicInteger notificationId = new AtomicInteger(0);

    Notification(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        for (String s : params) {
            publishProgress(s);
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        }
        return "Executed";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        for (String title: values) {
            sendNotification(title, notificationId.incrementAndGet());
        }
    }

    // sends notifications
    void sendNotification(String title, int notificationId) {
        String body = "";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(String.format("%s", title))
                .setContentText(String.format("%s", body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
        notificationManager.notify(notificationId, builder.build());
    }
}