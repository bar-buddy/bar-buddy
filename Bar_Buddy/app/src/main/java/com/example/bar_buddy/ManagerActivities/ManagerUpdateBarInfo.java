package com.example.bar_buddy.ManagerActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerUpdateBarInfo extends AppCompatActivity{
    private EditText barCover, barWait;
    private FirebaseAuth auth;
    private Button btnAddBarInfo;

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


        setContentView(R.layout.activity_manager_add_cover_wait);

        barCover = (EditText) findViewById(R.id.input_barCover);
        barWait = (EditText) findViewById(R.id.input_barWait);
        btnAddBarInfo = (Button) findViewById(R.id.btn_addBarCoverWait);
    }

    public void saveBarCoverWait(View v){
        String cover = barCover.getText().toString();
        String wait = barWait.getText().toString();

        if (TextUtils.isEmpty(cover)) {
            Toast.makeText(getApplicationContext(), "Enter a cover charge", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(wait)) {
            Toast.makeText(getApplicationContext(), "Enter a wait time", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();

        data.put("bar_cover", cover);
        data.put("bar_wait", wait);

        DocumentReference bar = db.collection("bars").document(bar_ids.get(0));

        bar.update("bar_cover", cover, "bar_wait", wait)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Bar Cover and Wait Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Bar Cover and Wait Not Saved", Toast.LENGTH_SHORT).show();
                    }
                });

        /*bar.update("bar_wait", wait)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Bar Cover and Wait Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Bar Cover and Wait Not Saved", Toast.LENGTH_SHORT).show();
                    }
                });*/

    }


}