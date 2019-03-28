package com.example.bar_buddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class BarDisplay extends AppCompatActivity {

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
                    extras.getString("bar_image")
            );
        }
    }

    private void setValues() {
        getSupportActionBar().setTitle(bar.getBar_name());
        TextView cover_tv = (TextView) findViewById(R.id.bar_display_cover_tv);
        TextView wait_time_tv = (TextView) findViewById(R.id.bar_display_wait_time_tv);
        ImageView header_image = (ImageView) findViewById(R.id.bar_display_image_header);

        cover_tv.setText(bar.getBar_cover());
        wait_time_tv.setText(bar.getBar_wait_time_minutes());
    }

    private void setListeners() {
        final Button menuBtn = (Button) findViewById(R.id.bar_display_menu_btn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(v.getContext(), BarMenu.class);
                getApplicationContext().startActivity(intent);
            }
        });
    }
}
