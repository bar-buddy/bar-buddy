package com.example.bar_buddy;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ToggleButton;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleBarsThroughFirestore {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    List<BarItem> bars;

    private interface FavCheckCallback {
        void onCallback(List<BarItem> bars);
    }

    private void checkFavorite(final FavCheckCallback callback, CollectionReference ref, String bar_id) {
        ref.whereEqualTo("bar_id", bar_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.e("adding", "stuff");
                            bars.add(new BarItem());
                        }
                    }
                });
    }

    public boolean isFavorite(String bar_id, final ToggleButton btn) {

        bars = new ArrayList<>();

        final CollectionReference favRef = db.collection("users").document(user.getUid()).collection("bars_favorites");
        checkFavorite(new FavCheckCallback() {
            @Override
            public void onCallback(List<BarItem> bars) {
                btn.setChecked(true);
            }
        }, favRef, bar_id);

        if(bars.size() > 0) return true;

        return false;
    }

    public void addFavorite(String bar_id) {
        Map<String, Object> bar = new HashMap<>();
        bar.put("bar_id", bar_id);

        db.collection("users").document(user.getUid()).collection("bars_favorites").add(bar);
    }

    public void removeFavorite(final String bar_id) {
        final CollectionReference favRef = db.collection("users").document(user.getUid()).collection("bars_favorites");
        favRef.whereEqualTo("bar_id", bar_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                favRef.document(document.getId()).delete();
                            }
                        }
                    }
                });
    }
}
