package com.example.litness.litness.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class RegisterDialog extends AlertDialog{

    private final Interface.WithStringListListener listener;

    private EditText email, pass0, pass1;

    RegisterDialog(Context ctx, Interface.WithStringListListener listener) {
        super(ctx);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);

        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        email = findViewById(R.id.registerdialog_input_email);
        pass0 = findViewById(R.id.registerdialog_input_password0);
        pass1 = findViewById(R.id.registerdialog_input_password1);

        Objects.requireNonNull((View) findViewById(R.id.registerdialog_button_register)).setOnClickListener(x -> {
            //make sure the email and passwords pass check
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() || !pass0.getText().toString().equals(pass1.getText().toString())) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                    email.setError("Email Required");
                if (!pass0.getText().toString().equals(pass1.getText().toString()))
                    pass0.setError("Passwords Must Match");
            }
            else {
                dismiss();
                if (listener != null)
                    listener.onEvent(getRegistrationInformation());
            }
        });
        Objects.requireNonNull((View) findViewById(R.id.registerdialog_button_close)).setOnClickListener(x -> dismiss());
    }

    private List<String> getRegistrationInformation() {
        List<String> info = new ArrayList<>();
        info.add(email.getText().toString());
        info.add(pass0.getText().toString());
        info.add(pass1.getText().toString());
        return info;
    }
}