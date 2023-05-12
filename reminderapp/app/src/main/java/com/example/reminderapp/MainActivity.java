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
    private String URL = "ssh://artemis_csub/home/stu/pcruz/public_html/Reminder/login.php";

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

        loginbutton = (Button) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);

        message = (TextView) findViewById(R.id.logintext);
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
        startService(new Intent(this, NotificationService.class));
    }

    // TODO: implement login
    public void login () {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        final Intent success = new Intent(this, ReminderList.class);

        if(!email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        startActivity(success);
                        finish();
                    } else if (response.equals("failure")) {
                        errorMsg = "Incorrect email and/or password.";
                        errortext.setText(errorMsg);
                        //Toast.makeText(MainActivity.this, "Invalid Login Id/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put( "email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            errorMsg = "Please input email and password.";
            errortext.setText(errorMsg);
            //Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
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
                //login();
                final Intent temp = new Intent(this, ReminderList.class);
                startActivity(temp);
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
        //});
        //thread.start();
    };

}