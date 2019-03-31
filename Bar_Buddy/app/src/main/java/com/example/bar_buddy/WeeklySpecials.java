package com.example.bar_buddy;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WeeklySpecials extends AppCompatActivity{
    private EditText specialName, specialDay, specialDescription;
    private FirebaseAuth auth;
    private Button btnAddWeeklySpecial;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        //final Map<String, Object> data = new HashMap<>();
        //final DocumentReference docRef = db.collection("bars").document("U2AraPs3G9bAGgGFwIDW").collection("weekly_specials").document("RauYNgHCafmj3m9CKruZ");

        //set the view now
        setContentView(R.layout.activity_add_weekly_special);
        specialName = (EditText) findViewById(R.id.input_WeeklySpecialName);
        specialDay = (EditText) findViewById(R.id.inputspecialday);
        specialDescription = (EditText) findViewById(R.id.input_weeklyspecialdescription);
        btnAddWeeklySpecial = (Button) findViewById(R.id.btn_addWeeklySpecial);

        btnAddWeeklySpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Map<String, Object> data = new HashMap<>();
                DocumentReference docRef = db.collection("bars").document("U2AraPs3G9bAGgGFwIDW").collection("weekly_specials").document("RauYNgHCafmj3m9CKruZ");

                data.put("special_day", day);
                data.put("special_description", description);
                data.put("special_name", name);
                docRef.set(data);
            }
        });
    }



}