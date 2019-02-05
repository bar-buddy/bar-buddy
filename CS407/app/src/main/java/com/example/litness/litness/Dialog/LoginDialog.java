package com.example.litness.litness.Dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class LoginDialog extends AlertDialog {
    private final Context ctx;
    private final Interface.WithStringListener listener;


    public LoginDialog(Context c, Interface.WithStringListener listener) {
        super(c);
        this.listener = listener;
        this.ctx = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);

        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Objects.requireNonNull((View) findViewById(R.id.login_button_login)).setOnClickListener(v -> {
            saveLoginInfo(((EditText) Objects.requireNonNull((View) findViewById(R.id.login_input_email))).getText().toString(), ((EditText) Objects.requireNonNull((View) findViewById(R.id.login_input_password))).getText().toString());
            dismiss();
            if(listener != null)
                listener.onEvent(((EditText) Objects.requireNonNull((View) findViewById(R.id.login_input_email))).getText().toString());
        });

        Objects.requireNonNull((View) findViewById(R.id.login_button_forgot)).setOnClickListener(v-> {
            InputDialog d = new InputDialog(ctx,"Email", x->
                new OkDialog(ctx,"", "A reset password link was sent to " + x, null).show());
            d.setCancelable(false);
            d.show();
        });

        //auto sign them in
        Objects.requireNonNull((View) findViewById(R.id.login_button_register)).setOnClickListener(v -> {
            dismiss();
            RegisterDialog d = new RegisterDialog(ctx, x-> saveLoginInfo(x.get(0),x.get(1)));
            d.setCancelable(false);
            d.show();
        } );

        Objects.requireNonNull((View) findViewById(R.id.login_button_close)).setOnClickListener(x -> dismiss());

    }

    private void saveLoginInfo(String email, String password) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Unm",email);
        editor.putString("Psw",password);
        editor.apply();
    }
}