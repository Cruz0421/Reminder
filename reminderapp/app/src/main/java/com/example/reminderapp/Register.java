package com.example.reminderapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText etName, etEmail, etPassword, etReenterPassword;
    private TextView tvStatus;
    private Button btnRegister;
    private String URL = "ssh://artemis_csub/home/stu/pcruz/public_html/Reminder/register.php";
    private String name, email, password, reenterPassword;
    TextView errortext;
    String errorMsg = "";
    Button loginbutton;
    Button registerbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.inputEmail);
        etPassword = findViewById(R.id.inputPass);
        etReenterPassword = findViewById(R.id.inputPass);
        //tvStatus = findViewById(R.id.tvStatus);
        btnRegister = findViewById(R.id.loginbutton);
        name = email = password = reenterPassword = "";

        errortext = findViewById(R.id.errortext);
        errortext.setText(errorMsg);
        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);
        registerbutton = findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);

    }

    public void save(View view) {
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();
        if(!password.equals(reenterPassword)) {
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("") && !email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        tvStatus.setText("Successfully registered.");
                        btnRegister.setClickable(false);
                    } else if (response.equals("failure")) {
                        tvStatus.setText("something is wrong");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put( "email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbutton:
                //what should happen when user clicks login
                //login();
                final Intent temp = new Intent(this, MainActivity.class);
                startActivity(temp);
                break;
            case R.id.registerbutton:
                // what should happen when user clicks register

                break;
        }
    }
}