package com.example.bar_buddy.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.Adapters.MenuAdapter;
import com.example.bar_buddy.AdapterItems.MenuItem;
import com.example.bar_buddy.R;
import com.example.bar_buddy.TabFragments.HomeTab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BarMenu extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference menuRef;

    MenuAdapter adapter;

    private List<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id = extras.getString("bar_id");
            menuRef = db.collection("bars").document(id).collection("menu");
        }

        menuItems = new ArrayList<>();
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<MenuItem> list) {
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView rvMenu = findViewById(R.id.menu_drinks_rv);
        rvMenu.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        /* Custom divider
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_expand_less));
        rvMenu.addItemDecoration(decoration);*/
        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MenuAdapter(this, menuItems);
        rvMenu.setAdapter(adapter);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private interface FirestoreCallback {
        void onCallback(List<MenuItem> list);
    }

    private void readData(final FirestoreCallback firestoreCallback) {
        menuRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                MenuItem b = document.toObject(MenuItem.class);

                                if(menuItems != null) {
                                    menuItems.add(b);
                                }
                            }
                            firestoreCallback.onCallback(menuItems);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
