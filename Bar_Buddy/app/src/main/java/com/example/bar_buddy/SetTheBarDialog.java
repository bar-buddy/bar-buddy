package com.example.bar_buddy;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.BarItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetTheBarDialog extends AlertDialog {

    Context ctx;

    private BarItem bar;
    private TextView dialogTitle;
    private Spinner waitTime;
    private EditText cover;

    public SetTheBarDialog(Context ctx, BarItem b) {
        super(ctx);
        ctx = ctx;
        bar = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_the_bar);
        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        /* Set Title */
        String title = "Set the Bar: " + bar.getBar_name();
        dialogTitle = findViewById(R.id.title_set_the_bar);
        dialogTitle.setText(title);

        //String[] times = new String[]{"< 5", "5-10", "10-15", "15-20", "20-30", "> 30"};
        /*List<String> times = new ArrayList<String>();
        times.add("< 5"); times.add("5-10"); times.add("10-15"); times.add("15-20");
        times.add("20-30"); times.add("> 30");*/
        String times[] = {"< 5 minutes", "5-10 minutes", "10-15 minutes", "15-20 minutes", "20-30 minutes", "> 30 minutes"};

        waitTime = (Spinner) findViewById(R.id.spinner_wait_time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waitTime.setAdapter(adapter);

        cover = (EditText) findViewById(R.id.edit_text_cover);

    }
}
