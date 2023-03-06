/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: 
*/



package com.example.remind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
    }

    public class Thx implements Runnable {
        //Thread thread = new Thread(new Runnable() {
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
        //});
        //thread.start();
    };


    // https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    // button press event
    @Override
    public void onClick(View v) {
        Thread thread = new Thread(new Thx());
        thread.start();
        Toast.makeText(this, "Taylor Hooser", Toast.LENGTH_SHORT).show();
        message.setText("Taylor Hooser");
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
