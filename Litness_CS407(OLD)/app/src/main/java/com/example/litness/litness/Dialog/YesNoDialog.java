package com.example.litness.litness.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;


import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.Objects;

public class YesNoDialog extends AlertDialog{

    private final String title;
    private final String text;
    private final Interface.YesNoHandler handler;

    public YesNoDialog(Context ctx, String title, String text, Interface.YesNoHandler handler) {
        super(ctx);
        this.title = title;
        this.text = text;
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_general);

        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_ok)).setVisibility(View.GONE);
        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_yes)).setVisibility(View.VISIBLE);
        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_no)).setVisibility(View.VISIBLE);

        ((TextView) Objects.requireNonNull((View) findViewById(R.id.generaldialog_alt_text))).setText(text);

        if (title == null)
            Objects.requireNonNull((View) findViewById(R.id.generaldialog_alt_title)).setVisibility(View.GONE);
        else
            ((TextView) Objects.requireNonNull((View) findViewById(R.id.generaldialog_alt_title))).setText(title);

        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_no)).setOnClickListener(x -> {
            dismiss();
            handler.onNoClicked();
        });

        Objects.requireNonNull((View) findViewById(R.id.generaldialog_button_yes)).setOnClickListener(x -> {
            dismiss();
            handler.onYesClicked();
        });
    }
}