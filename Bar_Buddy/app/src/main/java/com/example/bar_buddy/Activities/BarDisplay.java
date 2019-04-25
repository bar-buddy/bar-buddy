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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.AdapterItems.EventItem;
import com.example.bar_buddy.AdapterItems.UpdateItem;
import com.example.bar_buddy.AdapterItems.UserReviewItem;
import com.example.bar_buddy.AdapterItems.WeeklySpecialItem;
import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.Adapters.EventAdapter;
import com.example.bar_buddy.Adapters.UpdatesCardAdapter;
import com.example.bar_buddy.Adapters.WeeklyAdapter;
import com.example.bar_buddy.HandleBarsThroughFirestore;
import com.example.bar_buddy.R;
import com.example.bar_buddy.SetTheBarDialog;
import com.example.bar_buddy.UserDataDialog;
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

import org.w3c.dom.Text;

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

    private WeeklyAdapter weeklyAdapter;
    private List<WeeklySpecialItem> weeklySpecialsList;

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
                            setWeeklySpecialsRecycler();
                            setValues();
                            setListeners();

                        }
                    }
        });
    }

    private void setValues() {
        CollapsingToolbarLayout ctb = findViewById(R.id.toolbar_layout);
        ctb.setTitle(bar.getBar_name());

        final TextView cover_tv = (TextView) findViewById(R.id.bar_display_cover_tv);
        final TextView wait_time_tv = (TextView) findViewById(R.id.bar_display_wait_time_tv);
        TextView hours_operation = (TextView) findViewById(R.id.bar_display_hours_operation_tv);
        TextView description_tv = (TextView) findViewById(R.id.bar_display_description_tv);
        final ImageButton coverBtn = (ImageButton) findViewById(R.id.bar_display_cover_btn);

        ImageView header_image = (ImageView) findViewById(R.id.bar_display_image_header);

        String hours_operation_lead = " " + bar.getBar_hours_operation();

        String cover = "Cover: $" + bar.getBar_cover();
        String wait = "Wait time: " + bar.getBar_wait() + " minutes";
        cover_tv.setText(cover);
        wait_time_tv.setText(wait);

        readData(new FirestoreCallbackTwo() {
            @Override
            public void onCallback(int user_cover, int user_wait) {
                String cover, wait;
                if(user_cover >= 0) {
                    TextView cover_lead = (TextView) findViewById(R.id.cover_lead);
                    String avgCov = "Average Cover: ";
                    cover_lead.setText(avgCov);
                    cover = "$" + user_cover;
                    cover_tv.setText(cover);
                    coverBtn.setVisibility(View.VISIBLE);
                }
                if(user_wait >= 0) {
                    TextView wait_lead = (TextView) findViewById(R.id.wait_time_lead);
                    String waitAvg = "Average Wait time: ";
                    wait_lead.setText(waitAvg);
                    wait = user_wait + " mins";
                    wait_time_tv.setText(wait);
                }
            }
        });

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
        final ImageButton coverBtn = (ImageButton) findViewById(R.id.bar_display_cover_btn);

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

        coverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog userData = new UserDataDialog(BarDisplay.this, bar);
                userData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                userData.show();
            }
        });
    }

    private interface FirestoreCallbackTwo {
        void onCallback(int user_cover, int user_wait);
    }

    private void readData(final FirestoreCallbackTwo firestoreCallback) {
        db.collection("bars").document(bar.getBar_id()).collection("user_reviews")
                .orderBy("time_submitted", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int user_cover = 0, user_wait = 0, user_count = 0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserReviewItem review = document.toObject(UserReviewItem.class);
                                user_cover += Integer.parseInt(review.getBar_cover());
                                user_wait += Integer.parseInt(review.getBar_wait());
                                user_count++;
                            }
                            if(user_count > 0) {
                                user_cover /= user_count;
                                user_wait /= user_count;
                            } else if(user_count == 0) {
                                user_cover = -1;
                                user_wait = -1;
                            }

                            firestoreCallback.onCallback(user_cover, user_wait);
                        } else {
                            firestoreCallback.onCallback(-1, -1);
                        }
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

    private void setWeeklySpecialsRecycler() {
        RecyclerView rvCards = (RecyclerView) findViewById(R.id.weekly_specials_menu_rv);

        weeklySpecialsList = new ArrayList<WeeklySpecialItem>();

        weeklyAdapter = new WeeklyAdapter(this, weeklySpecialsList);
        rvCards.setAdapter(weeklyAdapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        getWeeklySpecials(new FirestoreCallback() {
            @Override
            public void onCallback(boolean result) {
                weeklyAdapter.notifyDataSetChanged();
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

    private void getWeeklySpecials(final FirestoreCallback firestoreCallback) {
        db.collection("bars")
                .document(bar.getBar_id())
                .collection("weekly_specials")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                WeeklySpecialItem w = document.toObject(WeeklySpecialItem.class);

                                weeklySpecialsList.add(w);
                                Log.e("add", "ing");
                            }
                            firestoreCallback.onCallback(true);
                        }
                    }
                });
    }
}
