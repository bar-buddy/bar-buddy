package com.example.litness.litness.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.Objects;

class InputDialog extends AlertDialog{

    private final String label;
    private final Interface.WithStringListener listener;

    public InputDialog(Context ctx, String label, Interface.WithStringListener listener) {
        super(ctx);
        this.label = label;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);

        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ((TextView) Objects.requireNonNull((View) findViewById(R.id.inputdialog_alt_label))).setText(label);

        Objects.requireNonNull((View) findViewById(R.id.inputdialog_button_confirm)).setOnClickListener(x -> {
            dismiss();
            if(listener != null)
                listener.onEvent(((EditText) Objects.requireNonNull((View) findViewById(R.id.inputdialog_input))).getText().toString());
        });
        Objects.requireNonNull((View) findViewById(R.id.inputdialog_button_close)).setOnClickListener(x -> dismiss());
    }
}