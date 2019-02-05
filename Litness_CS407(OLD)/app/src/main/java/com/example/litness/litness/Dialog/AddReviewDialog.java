package com.example.litness.litness.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class AddReviewDialog extends AlertDialog{

    private final String title;
    private final Interface.WithStringListListener listener;

    public AddReviewDialog(Context ctx, String title, Interface.WithStringListListener listener) {
        super(ctx);
        this.title = title;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addreview);

        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ((TextView) Objects.requireNonNull((View) findViewById(R.id.addreviewdialog_alt_title))).setText(title);

        Objects.requireNonNull((View) findViewById(R.id.addreviewdialog_button_add)).setOnClickListener(x -> {
            dismiss();
            if(listener != null)
                listener.onEvent(getReviewInfo());
        });
        Objects.requireNonNull((View) findViewById(R.id.addreviewdialog_button_close)).setOnClickListener(x -> dismiss());
    }

    private List<String> getReviewInfo() {
        List<String> info = new ArrayList<>();
        info.add(((EditText) Objects.requireNonNull((View) findViewById(R.id.addreviewdialog_alt_text))).getText().toString());
        info.add(((Integer) (((SeekBar) Objects.requireNonNull((View) findViewById(R.id.addreviewdialog_seekbar))).getProgress() + 1)).toString());
        return info;
    }
}