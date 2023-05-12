package com.example.reminderapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class notification extends AsyncTask<String, String, String> {

    private static final String TAG = "notification";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private Context ctx;
    private AtomicInteger notificationId = new AtomicInteger(0);

    notification(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        for (String s : params) {
            Log.e(TAG, s);

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

    void sendNotification(String title, int notificationId) {
        String body = "";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                //.setContentTitle(String.format("%s (id %d)", title, notificationId))
                .setContentTitle(String.format("%s", title))
                .setContentText(String.format("%s", body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false);
                // Add the action button
                //.addAction(R.drawable.ic_launcher_foreground, ctx.getString(R.string.snooze),
                        //snoozePendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

}
