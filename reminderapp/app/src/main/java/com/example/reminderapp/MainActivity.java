/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: Login page for app
*/

package com.example.reminderapp;


import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button loginbutton;
    Button registerbutton;
    TextView message;
    TextView errortext;
    EditText etEmail;
    EditText etPassword;

    String errorMsg = "";
    String threadString = "";
    String email = "";
    String password = "";

    database dbHandler;

    // main functionality
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new database(this);

        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);
        registerbutton = findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);

        message = findViewById(R.id.logintext);
        errortext = findViewById(R.id.errortext);
        errortext.setText(errorMsg);

        etEmail = findViewById(R.id.inputEmail);
        etPassword = findViewById(R.id.inputPass);

        // start thread
        Thread thread = new Thread(new Thx());
        thread.start();

    }

    // creates background service to send notifications even when app is off
    @Override
    protected void onStop() {
        super.onStop();
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra("email", email);
        startService(serviceIntent);
    }

    public boolean login () {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if(email.equals("")||password.equals(""))
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        else {
            Boolean checkuserpass = dbHandler.checkusernamepassword(email, password);
            if(checkuserpass == true) {
                Toast.makeText(this, "Sign in Success", Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(this, "Username or Password wrong", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    public void register(View view) {
        final Intent register = new Intent(MainActivity.this, Register.class);
        startActivity(register);
        finish();
    }

    // handler for click events
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbutton:
                //what should happen when user clicks login
                Intent temp = new Intent(this, ReminderList.class);
                if (login()) {
                    temp.putExtra("email", email);
                    startActivity(temp);
                }
                break;
            case R.id.registerbutton:
                // what should happen when user clicks register
                final Intent temp2 = new Intent(this, Register.class);
                startActivity(temp2);
                break;
        }
    }

    // thread that connects to file online
    public class Thx implements Runnable {
        @Override
        public void run() {
            try {
                java.net.URL url = new URL("https://cs.csub.edu/~thooser/3350/apptext.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    total.append(line + " ");
                }
                threadString = total.toString();
                in.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return;
        }
    };

}