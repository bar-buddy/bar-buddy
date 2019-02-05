package com.example.litness.litness.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.litness.litness.Client;
import com.example.litness.litness.Interface;
import com.example.litness.litness.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckInDialog extends AlertDialog{

    private final Interface.WithStringListListener listener;

    private SeekBar sb;
    private EditText coverOver, coverUnder, wait;

    public CheckInDialog(Context ctx, Interface.WithStringListListener listener) {
        super(ctx);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_checkin);

        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        sb = findViewById(R.id.checkindialog_litness);
        coverOver = findViewById(R.id.checkindialog_input_coverover);
        coverUnder = findViewById(R.id.checkindialog_input_coverunder);
        wait = findViewById(R.id.checkindialog_input_wait);

        sb.setProgress(Integer.parseInt(Client.activeBar.litness));
        coverOver.setHint(Client.activeBar.coverOver);
        coverUnder.setHint(Client.activeBar.coverUnder);
        if(Client.activeBar.wait.equals(""))
            wait.setHint("None");
        else
            wait.setHint(Client.activeBar.wait);

        Objects.requireNonNull((View) findViewById(R.id.checkindialog_button_checkin)).setOnClickListener(x -> {
            dismiss();
            if (listener != null)
                listener.onEvent(getCheckInInformation());
        });
        Objects.requireNonNull((View) findViewById(R.id.checkindialog_button_close)).setOnClickListener(x -> dismiss());
    }

    private List<String> getCheckInInformation() {
        List<String> info = new ArrayList<>();
        info.add(((Integer) (sb.getProgress() + 1)).toString());
        info.add(coverOver.getText().toString());
        info.add(coverUnder.getText().toString());
        info.add(wait.getText().toString());
        return info;
    }
}