package com.example.bar_buddy;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RetrieveBarDB {
    BarItem bar;
    DocumentReference barRef;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RetrieveBarDB(String id) {
        barRef = db.collection("bars").document(id);
    }

    private void getBar() {
        barRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            bar = documentSnapshot.toObject(BarItem.class);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}
