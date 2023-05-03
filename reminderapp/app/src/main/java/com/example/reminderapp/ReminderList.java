package com.example.reminderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReminderList extends AppCompatActivity implements View.OnClickListener{

    Button addReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderlist);

        addReminder = (Button) findViewById(R.id.addreminder);
        addReminder.setOnClickListener(this);
    }

    // https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    // button press event
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addreminder:
                //go to add reminder page
                Intent editReminder = new Intent(ReminderList.this, AddReminder.class);
                startActivity(editReminder);
                break;
        }
    }
}