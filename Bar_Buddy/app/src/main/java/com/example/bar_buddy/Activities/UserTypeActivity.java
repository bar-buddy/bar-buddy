package com.example.bar_buddy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bar_buddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserTypeActivity extends AppCompatActivity {

    private Button userButton, managerButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        userButton = (Button) findViewById(R.id.customer_button);
        managerButton = (Button) findViewById(R.id.manager_button);

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCustomer(user);
                startActivity(new Intent(UserTypeActivity.this, MainActivity.class));
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newManager(user);
                startActivity(new Intent(UserTypeActivity.this, ManagerMainActivity.class));
            }
        });

    }

    public void newManager(FirebaseUser u){
        CollectionReference users = db.collection("users");
        Map<String, Object> user = new HashMap<>();
        user.put("user_type", "manager");
        users.document(u.getUid()).set(user);
    }

    public void newCustomer(FirebaseUser u){
        CollectionReference users = db.collection("users");
        Map<String, Object> user = new HashMap<>();
        user.put("user_type", "customer");
        users.document(u.getUid()).set(user);
    }

}
