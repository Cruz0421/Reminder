/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose:
*/

package com.example.notificationtest;

// https://github.com/michaelJustin/android-notification

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.notificationtest.MyBroadcastReceiver.ACTION_SNOOZE;
import static com.example.notificationtest.MyBroadcastReceiver.EXTRA_NOTIFICATION_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String TAG = "main";

    private boolean isTouch = false;

    Button btnClick;
    TextView message;
    TextView outsourcetext;
    String strxx = "Please click again!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick = (Button) findViewById(R.id.button);
        btnClick.setOnClickListener(MainActivity.this);
        message = (TextView) findViewById(R.id.message);
        outsourcetext = (TextView) findViewById(R.id.outsourcetext);

        createNotificationChannel();

        Log.e(TAG, "onCreate");

        LongOperation lo = new LongOperation(this);
        lo.execute("notif title");
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private class LongOperation extends AsyncTask<String, String, String> {

        private static final String TAG = "longoperation";
        private Context ctx;
        private AtomicInteger notificationId = new AtomicInteger(0);

        LongOperation(Context ctx) {
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

            // Create an explicit intent for an Activity in your app
        /* Intent intent = new Intent(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0); */

            Intent snoozeIntent = new Intent(ctx, MyBroadcastReceiver.class);
            snoozeIntent.setAction(ACTION_SNOOZE);
            snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);

            Log.e(TAG, snoozeIntent.getExtras().toString());

            Log.e(TAG, "snoozeIntent id: " + snoozeIntent.getIntExtra(EXTRA_NOTIFICATION_ID, -1));

            PendingIntent snoozePendingIntent =
                    PendingIntent.getBroadcast(ctx, notificationId, snoozeIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    //.setContentTitle(String.format("%s (id %d)", title, notificationId))
                    .setContentTitle(String.format("%s", title))
                    .setContentText("notification body")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(false)
                    // Add the action button
                    .addAction(R.drawable.ic_launcher_foreground, ctx.getString(R.string.snooze),
                            snoozePendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, builder.build());
        }
    }

    // thread checks for updates
    public class Thx implements Runnable {
        @Override
        public void run() {
            try {
                URL url = new URL("https://cs.csub.edu/~thooser/3350/apptext.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    total.append(line + " ");
                }
                strxx = total.toString();
                in.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return;
        }
    };

    // https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    // button press event
    @Override
    public void onClick(View v) {
        // starts thread that reads from file
        Thread thread = new Thread(new Thx());
        thread.start();
        // toast popup message
        Toast.makeText(this, "Toast text", Toast.LENGTH_SHORT).show();
        message.setText("Toast Text");
        outsourcetext.setText(strxx);
    }

    // https://stackoverflow.com/questions/3142670/how-do-i-detect-touch-input-on-the-android
    // swipe event
    int X0 = 0;
    int X1 = 0;
    int Y0 = 0;
    int Y1 = 0;
    int Xthreshold = 200;
    int Ythreshold = 1000;
    boolean Xcheck = true;
    boolean Ycheck = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: // begin swiping motion
                X0 = (int) event.getX();
                Y0 = (int) event.getY();
                isTouch = true;
                break;

            case MotionEvent.ACTION_MOVE: // end swiping motion
                X1 = (int) event.getX();
                Y1 = (int) event.getY();

                if(Math.abs(X0 - X1) > Xthreshold){
                    Xcheck = false;
                }

                if(Y1 - Y0 >= 1000){
                    Ycheck = true;
                }

                if(Xcheck && Ycheck){
                    //Toast.makeText(this, "YEAH BABY", Toast.LENGTH_SHORT).show();
                    this.finishAffinity(); //close app
                }
                break;

            case MotionEvent.ACTION_UP:
                X0 = 0;
                X1 = 0;
                Y0 = 0;
                Y1 = 0;
                Xcheck = true;
                Ycheck = false;
                break;
        }
        return true;
    }
}
