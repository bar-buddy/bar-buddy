package com.example.litness.litness.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;


import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.Objects;

class OkDialog extends AlertDialog{

    private final String title;
    private final String text;
    private final Interface.WithVoidListener listener;

    public OkDialog(Context ctx, String title, String text, Interface.WithVoidListener listener) {
        super(ctx);
        this.title = title;
        this.text = text;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_general);

        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_ok)).setVisibility(View.VISIBLE);
        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_yes)).setVisibility(View.GONE);
        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_no)).setVisibility(View.GONE);

        ((TextView) Objects.requireNonNull((View) findViewById(R.id.generaldialog_alt_text))).setText(text);

        if (title == null)
            Objects.requireNonNull((View) findViewById(R.id.generaldialog_alt_title)).setVisibility(View.GONE);
        else ((TextView)
                Objects.requireNonNull((View) findViewById(R.id.generaldialog_alt_title))).setText(title);

        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_ok)).setOnClickListener(x -> {
            if(listener != null)
                listener.onEvent();
            dismiss();
        });
    }
}
