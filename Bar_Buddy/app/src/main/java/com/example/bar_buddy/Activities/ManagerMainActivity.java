package com.example.bar_buddy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bar_buddy.R;
import com.example.bar_buddy.WeeklySpecials;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerMainActivity extends AppCompatActivity {

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
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ManagerMainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
