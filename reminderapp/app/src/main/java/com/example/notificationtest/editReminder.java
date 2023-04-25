package com.example.notificationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class editReminder extends AppCompatActivity implements View.OnClickListener{

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editreminder);

        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
    }

    // https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    // button press event
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                Toast.makeText(this, "adding reminder", Toast.LENGTH_SHORT).show();
                Intent reminderPage = new Intent(editReminder.this, reminderPage.class);
                startActivity(reminderPage);
                break;
        }
    }
}
