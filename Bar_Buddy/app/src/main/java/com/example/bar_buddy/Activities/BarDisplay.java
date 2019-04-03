package com.example.bar_buddy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.HandleBarsThroughFirestore;
import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class BarDisplay extends AppCompatActivity {

    boolean isSpecialsExpanded = true;

    BarItem bar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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
        final Button specialsExpandBtn = (Button) findViewById(R.id.expand_button);
        final Button favBtn = (Button) findViewById(R.id.bar_display_fav_btn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(BarDisplay.this, BarMenu.class);
                intent.putExtra("bar_id", bar.getBar_id());
                BarDisplay.this.startActivity(intent);
            }
        });

        specialsExpandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSpecialsExpanded) {
                    //specialsExpandBtn.setBackground(ActivityCompat.getDrawable(getApplicationContext(), R.drawable.ic_expand_less));
                    specialsExpandBtn.setBackground(ActivityCompat.getDrawable(getApplicationContext(), R.drawable.ic_expand_more));
                    findViewById(R.id.specials_events_visible_list).setVisibility(View.GONE);
                    isSpecialsExpanded = false;
                } else {
                    specialsExpandBtn.setBackground(ActivityCompat.getDrawable(getApplicationContext(), R.drawable.ic_expand_less));
                    findViewById(R.id.specials_events_visible_list).setVisibility(View.VISIBLE);
                    isSpecialsExpanded = true;
                }
            }
        });

        boolean cur;

        final CollectionReference favRef = db.collection("users").document(user.getUid()).collection("bars_favorites");
        checkFavorite(new FavCheckCallback() {
            @Override
            public void onCallback(boolean result) {
                if(result) {
                    isFav = true;

                    int imgResource = R.drawable.ic_favorite_bar_card_selected;
                    favBtn.setCompoundDrawablesWithIntrinsicBounds(0, imgResource, 0, 0);
                    favBtn.setCompoundDrawablePadding(8);

                    String unfav = "Unfavorite";
                    favBtn.setText(unfav);
                }

                favBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFav) {
                            isFav = false;

                            int imgResource = R.drawable.ic_favorite_bar_card;
                            favBtn.setCompoundDrawablesWithIntrinsicBounds(0, imgResource, 0, 0);
                            //favBtn.setCompoundDrawablePadding(8);

                            String unfav = "Favorite";
                            favBtn.setText(unfav);

                            new HandleBarsThroughFirestore().removeFavorite(bar.getBar_id());
                        } else {
                            isFav = true;

                            int imgResource = R.drawable.ic_favorite_bar_card_selected;
                            favBtn.setCompoundDrawablesWithIntrinsicBounds(0, imgResource, 0, 0);
                            //favBtn.setCompoundDrawablePadding(8);

                            String unfav = "Unfavor";
                            favBtn.setText(unfav);

                            new HandleBarsThroughFirestore().addFavorite(bar.getBar_id());
                        }
                    }
                });
            }
        }, favRef, bar.getBar_id());

    }

    private interface FavCheckCallback {
        void onCallback(boolean result);
    }

    private void checkFavorite(final FavCheckCallback callback, CollectionReference ref, final String bar_id) {
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
}
