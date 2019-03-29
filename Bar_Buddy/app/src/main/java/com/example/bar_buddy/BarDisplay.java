package com.example.bar_buddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class BarDisplay extends AppCompatActivity {

    boolean isSpecialsExpanded = true;

    BarItem bar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        setValues();

        setListeners();
    }

    private void buildBar() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            bar = new BarItem(
                    extras.getString("bar_name"),
                    extras.getString("bar_cover"),
                    extras.getString("bar_wait_time_minutes"),
                    extras.getString("bar_description"),
                    extras.getString("bar_phone"),
                    extras.getString("bar_address"),
                    extras.getString("bar_image"),
                    extras.getString("bar_hours_operation")
            );
        }
    }

    private void setValues() {
        getSupportActionBar().setTitle(bar.getBar_name());
        TextView cover_tv = (TextView) findViewById(R.id.bar_display_cover_tv);
        TextView wait_time_tv = (TextView) findViewById(R.id.bar_display_wait_time_tv);
        TextView hours_operation = (TextView) findViewById(R.id.bar_display_hours_operation_tv);
        TextView description_tv = (TextView) findViewById(R.id.bar_display_description_tv);
        ImageView header_image = (ImageView) findViewById(R.id.bar_display_image_header);

        String cover_lead = "Cover: $" + bar.getBar_cover();
        String wait_time_lead = "Wait time: " + bar.getBar_wait() + " minutes";
        String hours_operation_lead = "Hours of operation: " + bar.getBar_hours_operation();

        cover_tv.setText(cover_lead);
        wait_time_tv.setText(wait_time_lead);
        hours_operation.setText(hours_operation_lead);
        description_tv.setText(bar.getBar_description());
    }

    private void setListeners() {
        final Button menuBtn = (Button) findViewById(R.id.bar_display_menu_btn);
        final Button specialsExpandBtn = (Button) findViewById(R.id.expand_button);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(BarDisplay.this, BarMenu.class);
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


    }
}
