package com.example.bar_buddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.bar_buddy.Activities.LoginActivity;
import com.example.bar_buddy.Activities.MainActivity;
import com.example.bar_buddy.ManagerActivities.ManagerMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserCheck extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null){
            startActivity(new Intent(UserCheck.this, LoginActivity.class));
            finish();
        }
        else {
            getUserType();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);
    }

    public void getUserType(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                String t = doc.getString("user_type");
                if (TextUtils.isEmpty(t)){
                    Toast.makeText(UserCheck.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserCheck.this, LoginActivity.class));
                    finish();
                }
                else if (t.equals("manager")){
                    startActivity(new Intent(UserCheck.this, ManagerMainActivity.class));
                    finish();
                }
                else if (t.equals("customer")){
                    startActivity(new Intent(UserCheck.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
}
