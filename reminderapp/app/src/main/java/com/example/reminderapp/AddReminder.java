package com.example.reminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddReminder extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Button saveButton;
    Button setdate;
    Button settime;
    TextView dateselected;
    TextView timeselected;
    EditText reminderName;
    EditText etnotes;
    String[] repeatChoices;

    // data to be stored in DB
    String name;
    String date;
    String time;
    String repeat;
    String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addreminder);

        // buttons
        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
        setdate = (Button) findViewById(R.id.setdate);
        setdate.setOnClickListener(this);
        settime = (Button) findViewById(R.id.settime);
        settime.setOnClickListener(this);

        // text
        dateselected = (TextView) findViewById(R.id.dateselected);
        timeselected = (TextView) findViewById(R.id.timeselected);
        reminderName = findViewById(R.id.etname);
        etnotes = findViewById(R.id.etnotes);

        // spiner stuff
        Spinner spin = findViewById(R.id.repeatSpinner);
        spin.setOnItemSelectedListener(this);
        repeatChoices = new String[]{"Hourly", "Daily", "Weekly", "Monthly", "Yearly"};
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, repeatChoices);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                // save reminder
                // write data to DB
                name = reminderName.getText().toString().trim();
                date = dateselected.getText().toString().trim();
                time = timeselected.getText().toString().trim();
                // repeat saved in listener
                notes = etnotes.getText().toString().trim();

                Intent reminderPage = new Intent(AddReminder.this, ReminderList.class);
                startActivity(reminderPage);
                break;
            case R.id.setdate:
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReminder.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateselected.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
                break;
            case R.id.settime:
                final Calendar c2 = Calendar.getInstance();
                int hour = c2.get(Calendar.HOUR_OF_DAY);
                int minute = c2.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReminder.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeselected.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
                break;
        }
    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method
    @Override
    public void onItemSelected(AdapterView arg0, View arg1, int position, long id)
    {
        repeat = repeatChoices[position];
    }

    @Override
    public void onNothingSelected(AdapterView arg0)
    {
        // Auto-generated method stub
    }
}
