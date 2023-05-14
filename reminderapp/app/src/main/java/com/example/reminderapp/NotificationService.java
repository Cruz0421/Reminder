/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: Creates a background service so notifications can be sent even when app is not running
*/

package com.example.reminderapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    int secDelay = 15;

    notification notif = new notification(this);
    String name = "placeholder";
    Date currentTime = Calendar.getInstance().getTime();
    Date reminderTime;

    Calendar c;
    Log log;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    // what happens when BG service starts
    // TODO: fix crash after task is done
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    // stub: what happens when BG service is created
    @Override
    public void onCreate() {
        // TODO: check if email is null
        // if null, dont start the bg service

        //Intent thisIntent = getIntent();
        //String email = thisIntent.getStringExtra("email");
    }

    // stops timer when background service stops
    @Override
    public void onDestroy() {
        stoptimertask();
        super.onDestroy();
    }

    final Handler handler = new Handler();

    // starts timer
    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();

        timer.schedule(timerTask, 5000, secDelay * 1000);
    }

    // stops timer, if not already nuill
    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // task that timer does
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //TODO: notification function
                            // currently causes crashes, commented out
                            // retrieve reminder information from DB
                            // and loop, checking if its time to get a reminder

                        /*
                        currentTime = Calendar.getInstance().getTime();
                        log.d("tag", "current time: " + currentTime);

                        c = Calendar.getInstance();
                        c.set(Calendar.MONTH, 5);
                        c.set(Calendar.DATE, 12);
                        c.set(Calendar.YEAR, 2023);
                        c.set(Calendar.HOUR, 3);
                        c.set(Calendar.MINUTE, 52);
                        reminderTime = c.getTime();

                        log.d("tag", "reminder time: " + reminderTime);

                        if (currentTime.compareTo(reminderTime) < 0) {
                            log.d("tag", "notif should send");
                            notif.execute(name);
                        }
                         */
                    }
                });
            }
        };
    }
}