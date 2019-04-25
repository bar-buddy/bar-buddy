package com.example.bar_buddy.ManagerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeeklySpecials extends AppCompatActivity{
    private EditText specialName, specialDay, specialDescription;
    private FirebaseAuth auth;
    private Button btnAddWeeklySpecial;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Map<String, Object> data = new HashMap<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<String> bar_ids = new ArrayList<>();

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


        setContentView(R.layout.activity_add_weekly_special);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_weekly_special2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        specialName = (EditText) findViewById(R.id.input_WeeklySpecialName);
        specialDay = (EditText) findViewById(R.id.inputspecialday);
        specialDescription = (EditText) findViewById(R.id.input_weeklyspecialdescription);
        btnAddWeeklySpecial = (Button) findViewById(R.id.btn_addWeeklySpecial);
    }

    public void saveWeeklySpecial(View v){
        String name = specialName.getText().toString();
        String day = specialDay.getText().toString();
        String description = specialDescription.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter a Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(day)) {
            Toast.makeText(getApplicationContext(), "Enter a Day", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Enter a Description", Toast.LENGTH_SHORT).show();
            return;
        }

        data.put("special_day", day);
        data.put("special_description", description);
        data.put("special_name", name);

        db.collection("bars").document(bar_ids.get(0)).collection("weekly_specials").document().set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public  void onSuccess(Void aVoid){
                        Toast.makeText(getApplicationContext(), "Weekly Special Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Weekly Special Not Saved", Toast.LENGTH_SHORT).show();
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
                        specialDay.setText(SelectedDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}