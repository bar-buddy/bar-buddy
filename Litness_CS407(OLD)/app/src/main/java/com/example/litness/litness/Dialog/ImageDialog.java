package com.example.litness.litness.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.litness.litness.R;

import java.util.List;

public class ImageDialog extends AlertDialog {

    private final List<Integer> data;
    private int index;

    private ImageView img;
    private ImageButton leftButton;
    private ImageButton rightButton;

    public ImageDialog(Context c, List<Integer> d, int i) {
        super(c, R.style.DialogTheme);
        data = d;
        index = i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image);

        img = findViewById(R.id.imagedialog_image);
        leftButton = findViewById(R.id.imagedialog_button_left);
        rightButton = findViewById(R.id.imagedialog_buttton_right);

        img.setImageResource(data.get(index));
        setVisibility(index);

        leftButton.setOnClickListener(x-> {
            if(index > 0) {
                img.setImageResource(data.get(index - 1));
                index -= 1;
                setVisibility(index);
            }
        });

        rightButton.setOnClickListener(x-> {
            if(index < data.size() - 1) {
                img.setImageResource(data.get(index + 1));
                index += 1;
                setVisibility(index);
            }
        });

        findViewById(R.id.imagedialog_button_close).setOnClickListener(
                x-> dismiss());
    }

    private void setVisibility(int index) {
        if(index < 1)
            leftButton.setVisibility(View.INVISIBLE);
        else
            leftButton.setVisibility(View.VISIBLE);
        if(index == data.size() - 1)
            rightButton.setVisibility(View.INVISIBLE);
        else
            rightButton.setVisibility(View.VISIBLE);
    }
}
