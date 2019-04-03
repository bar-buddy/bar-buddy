package com.example.bar_buddy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Event extends AppCompatActivity{
    private EditText eventName, eventDay, eventDescription;
    private FirebaseAuth auth;
    private Button btnAddEvent;

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

        setContentView(R.layout.activity_add_event);

        eventName = (EditText) findViewById(R.id.input_eventName);
        eventDay = (EditText) findViewById(R.id.input_eventDay);
        eventDescription = (EditText) findViewById(R.id.input_eventDescription);
        btnAddEvent = (Button) findViewById(R.id.btn_addEvent);
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
            Toast.makeText(getApplicationContext(), "Enter a Day", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Enter a Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();

        data.put("event_date", day);
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
}