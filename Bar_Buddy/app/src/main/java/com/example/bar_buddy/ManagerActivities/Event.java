package com.example.bar_buddy.ManagerActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Event extends AppCompatActivity{
    private EditText eventName, eventDay, eventDescription;
    private FirebaseAuth auth;
    private Button btnAddEvent;

    private EditText txtDate, txtStartTime, txtEndTime;
    private Button btnDatePicker, btnStartTimePicker, btnEndTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<String> bar_ids = new ArrayList<>();

    Map<String, Object> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        db.collection("users").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        bar_ids.add((String) documentSnapshot.get("bar_id"));
                    }
                });

        setContentView(R.layout.activity_add_event);

        eventName = (EditText) findViewById(R.id.input_eventName);
        eventDay = (EditText) findViewById(R.id.input_eventDate);
        eventDescription = (EditText) findViewById(R.id.input_eventDescription);
        btnAddEvent = (Button) findViewById(R.id.btn_addEvent);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnStartTimePicker=(Button)findViewById(R.id.btn_start_time);
        btnEndTimePicker=(Button)findViewById(R.id.btn_end_time);

        txtDate=(EditText)findViewById(R.id.input_eventDate);
        txtStartTime=(EditText)findViewById(R.id.in_start_time);
        txtEndTime=(EditText)findViewById(R.id.in_end_time);
    }

    public void saveEvent(View v){
        String name = eventName.getText().toString();
        String day = eventDay.getText().toString();
        String description = eventDescription.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter a Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(day)) {
            Toast.makeText(getApplicationContext(), "Enter a Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Enter a Description", Toast.LENGTH_SHORT).show();
            return;
        }

        data.put("event_description", description);
        data.put("event_name", name);

        db.collection("bars").document(bar_ids.get(0)).collection("events").document().set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public  void onSuccess(Void aVoid){
                        Toast.makeText(getApplicationContext(), "Event Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Event Not Saved", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void selectDate(View v){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String SelectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate.setText(SelectedDate);
                        data.put("event_date", SelectedDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void selectStartTime(View v){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String StartTime = hourOfDay + ":" + minute;
                        txtStartTime.setText(StartTime);
                        data.put("event_start_time", StartTime);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void selectEndTime(View v){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String EndTime = hourOfDay + ":" + minute;
                        txtEndTime.setText(EndTime);
                        data.put("event_end_time", EndTime);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}