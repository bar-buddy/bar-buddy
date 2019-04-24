package com.example.bar_buddy;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.firebase.Timestamp.now;

public class SetTheBarDialog extends AlertDialog {

    private BarItem bar;
    private TextView dialogTitle;
    //private Spinner waitTime;
    private EditText cover, waitTime;
    private Button submit;

    public SetTheBarDialog(Context ctx, BarItem b) {
        super(ctx);
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

        cover = (EditText) findViewById(R.id.edit_text_cover);
        waitTime = (EditText) findViewById(R.id.edit_text_wait_time);

        submit = (Button) findViewById(R.id.submit_set_the_bar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSetTheBar();
            }
        });

    }

    private void submitSetTheBar() {
        String coverInput = cover.getText().toString();
        String waitTimeInput = waitTime.getText().toString();

        if(coverInput.equals("") || waitTimeInput.equals("")) {
            Toast.makeText(getContext(), "ERROR: Please input valid cover and wait time", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();

        data.put("bar_cover", coverInput);
        data.put("bar_wait", waitTimeInput);
        data.put("time_submitted", now());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("bars").document(bar.getBar_id())
                .collection("user_reviews").add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dismiss();
                        Toast.makeText(getContext(), "Thanks for Setting the Bar!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "There was an issue submitting", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
