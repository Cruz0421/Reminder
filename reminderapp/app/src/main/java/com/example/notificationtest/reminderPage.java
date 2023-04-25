package com.example.notificationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class reminderPage extends AppCompatActivity implements View.OnClickListener{

    Button addReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        addReminder = (Button) findViewById(R.id.addreminder);
        addReminder.setOnClickListener(this);
    }

    // https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    // button press event
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addreminder:
                //Toast.makeText(this, "adding reminder", Toast.LENGTH_SHORT).show();
                Intent editReminder = new Intent(reminderPage.this, editReminder.class);
                startActivity(editReminder);
                break;
        }
    }
}