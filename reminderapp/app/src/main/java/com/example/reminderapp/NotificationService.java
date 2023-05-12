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

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    int Your_X_SECS = 15;

    Notification notif = new Notification(this);
    String name = "placeholder";
    Date currentTime = Calendar.getInstance().getTime();
    Date reminderTime;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    // what happens when BG service starts
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    // stub: what happens when BG service is created
    @Override
    public void onCreate() {
        // stub
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

        //after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000);
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
                        //TODO CALL NOTIFICATION FUNC
                        // for each reminder:
                            // check if current time matches
                                //notif.execute(name);
                    }
                });
            }
        };
    }
}