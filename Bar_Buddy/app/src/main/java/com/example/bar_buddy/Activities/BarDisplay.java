package com.example.bar_buddy.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.AdapterItems.EventItem;
import com.example.bar_buddy.AdapterItems.UpdateItem;
import com.example.bar_buddy.Adapters.EventAdapter;
import com.example.bar_buddy.Adapters.UpdatesCardAdapter;
import com.example.bar_buddy.HandleBarsThroughFirestore;
import com.example.bar_buddy.R;
import com.example.bar_buddy.SetTheBarDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BarDisplay extends AppCompatActivity {

    boolean isSpecialsExpanded = true;

    BarItem bar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private UpdatesCardAdapter updatesCardAdapter;
    private List<UpdateItem> updatesList;

    private EventAdapter eventAdapter;
    private List<EventItem> eventsList;

    boolean isFav = false;

    BarDisplay() {
        //necessary zero argument constructor
    }

    BarDisplay(BarItem b) {
        this.bar = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bar_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildBar();
    }

    private void buildBar() {
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("bar_id");

        db.collection("bars").document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            bar = task.getResult().toObject(BarItem.class);
                            bar.setBar_id(task.getResult().getId());

                            setUpdatesRecycler();
                            setEventsRecycler();
                            setValues();
                            setListeners();

                        }
                    }
        });
    }

    private void setValues() {
        CollapsingToolbarLayout ctb = findViewById(R.id.toolbar_layout);
        ctb.setTitle(bar.getBar_name());

        TextView cover_tv = (TextView) findViewById(R.id.bar_display_cover_tv);
        TextView wait_time_tv = (TextView) findViewById(R.id.bar_display_wait_time_tv);
        TextView hours_operation = (TextView) findViewById(R.id.bar_display_hours_operation_tv);
        TextView description_tv = (TextView) findViewById(R.id.bar_display_description_tv);

        ImageView header_image = (ImageView) findViewById(R.id.bar_display_image_header);

        String cover_lead = " $" + bar.getBar_cover();
        String wait_time_lead = " " + bar.getBar_wait() + " minutes";
        String hours_operation_lead = " " + bar.getBar_hours_operation();

        cover_tv.setText(cover_lead);
        wait_time_tv.setText(wait_time_lead);
        hours_operation.setText(hours_operation_lead);
        description_tv.setText(bar.getBar_description());

        Picasso.get()
                .load(bar.getBar_image())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.no_image_available)
                .into(header_image);
    }

    private void setListeners() {
        final Button menuBtn = (Button) findViewById(R.id.bar_display_menu_btn);
        final Button favBtn = (Button) findViewById(R.id.bar_display_fav_btn);
        final Button directionsBtn = (Button) findViewById(R.id.bar_display_directions_btn);
        final Button setTheBarBtn = (Button) findViewById(R.id.bar_display_set_the_bar_btn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(BarDisplay.this, BarMenu.class);
                intent.putExtra("bar_id", bar.getBar_id());
                BarDisplay.this.startActivity(intent);
            }
        });

        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=" + Uri.encode(bar.getBar_name() + " " + bar.getBar_address()))));
            }
        });

        final CollectionReference favRef = db.collection("users").document(user.getUid()).collection("bars_favorites");
        checkFavorite(new FirestoreCallback() {
            @Override
            public void onCallback(boolean result) {
                if(result) {
                    isFav = true;
                    favBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_bar_card_selected, 0, 0);
                    String unfav = "Unfavorite";
                    favBtn.setText(unfav);
                }

                favBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFav) {
                            isFav = false;
                            favBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_bar_card, 0, 0);
                            String unfav = "Favorite";
                            favBtn.setText(unfav);
                            new HandleBarsThroughFirestore().removeFavorite(bar.getBar_id());
                        } else {
                            isFav = true;
                            favBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_bar_card_selected, 0, 0);
                            String unfav = "Unfavorite";
                            favBtn.setText(unfav);
                            new HandleBarsThroughFirestore().addFavorite(bar.getBar_id());
                        }
                    }
                });
            }
        }, favRef, bar.getBar_id());

        setTheBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new SetTheBarDialog(BarDisplay.this, bar).show();
                AlertDialog STB = new SetTheBarDialog(BarDisplay.this, bar);
                STB.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                STB.show();
            }
        });

    }

    private void setUpdatesRecycler() {
        RecyclerView rvCards = (RecyclerView) findViewById(R.id.updates_menu_rv);

        updatesList = new ArrayList<UpdateItem>();

        updatesCardAdapter = new UpdatesCardAdapter(this, updatesList);
        rvCards.setAdapter(updatesCardAdapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        getUpdates(new FirestoreCallback() {
            @Override
            public void onCallback(boolean result) {
                updatesCardAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setEventsRecycler() {
        RecyclerView rvCards = (RecyclerView) findViewById(R.id.events_menu_rv);

        eventsList = new ArrayList<EventItem>();

        eventAdapter = new EventAdapter(this, eventsList);
        rvCards.setAdapter(eventAdapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        getEvents(new FirestoreCallback() {
            @Override
            public void onCallback(boolean result) {
                eventAdapter.notifyDataSetChanged();
            }
        });
    }

    private interface FirestoreCallback {
        void onCallback(boolean result);
    }

    private void checkFavorite(final FirestoreCallback callback, CollectionReference ref, final String bar_id) {
        ref.whereEqualTo("bar_id", bar_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean result = false;
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                result = true;
                                Log.e(bar_id, (String) document.get("bar_id"));
                            }


                        }
                        callback.onCallback(result);
                    }
                });
    }

    private void getUpdates(final FirestoreCallback firestoreCallback) {
        db.collection("updates")
                //.whereEqualTo("bar_id", bar.getBar_id())
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("bar_id").equals(bar.getBar_id())) {
                                    UpdateItem u = document.toObject(UpdateItem.class);
                                    u.setUpdate_id(document.getId());

                                    updatesList.add(u);
                                }
                            }
                            firestoreCallback.onCallback(true);
                        }
                    }
                });
    }
    
    private void getEvents(final FirestoreCallback firestoreCallback) {
        db.collection("bars")
                .document(bar.getBar_id())
                .collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventItem e = document.toObject(EventItem.class);

                                eventsList.add(e);
                            }
                            firestoreCallback.onCallback(true);
                        }
                    }
                });
    }
}
