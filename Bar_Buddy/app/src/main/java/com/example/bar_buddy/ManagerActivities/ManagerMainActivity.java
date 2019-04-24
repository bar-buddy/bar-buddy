package com.example.bar_buddy.ManagerActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bar_buddy.Activities.LoginActivity;
import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ManagerMainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<BarItem> bar;
    private String bar_id;
    private BarCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        Button logoutBtn;
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        Button addSpecials = findViewById(R.id.addWeeklySpecial);
        addSpecials.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(ManagerMainActivity.this, WeeklySpecials.class);
                ManagerMainActivity.this.startActivity(intent);
            }
        });

        Button addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Intent intent2;
                intent2 = new Intent(ManagerMainActivity.this, Event.class);
                ManagerMainActivity.this.startActivity(intent2);
            }
        });

        Button addCoverAndWait = findViewById(R.id.addCoverAndWait);
        addCoverAndWait.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Intent intent3;
                intent3 = new Intent(ManagerMainActivity.this, ManagerUpdateBarInfo.class);
                ManagerMainActivity.this.startActivity(intent3);
            }
        });

        bar = new ArrayList<BarItem>();

        RecyclerView rvCards = (RecyclerView) findViewById(R.id.your_bar_recyclerview);
        rvCards.setLayoutManager(new LinearLayoutManager(ManagerMainActivity.this));

        adapter = new BarCardAdapter(ManagerMainActivity.this, bar);
        rvCards.setAdapter(adapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        bar_id = (String) documentSnapshot.get("bar_id");
                        Log.e("b", bar_id);

                        readData(new FirestoreCallback() {
                            @Override
                            public void onCallback(List<BarItem> list) {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }

    private interface FirestoreCallback {
        void onCallback(List<BarItem> list);
    }

    private void readData(final FirestoreCallback firestoreCallback) {
        db.collection("bars").document(bar_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            BarItem b = task.getResult().toObject(BarItem.class);
                            b.setBar_id(bar_id);
                            if(bar != null) {
                                bar.add(b);
                                Log.e("add","ing");
                            }
                            firestoreCallback.onCallback(bar);
                        }
                    }
                });
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ManagerMainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
