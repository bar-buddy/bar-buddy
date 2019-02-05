package com.example.litness.litness.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.litness.litness.Adapter.ReviewDialogAdapter;
import com.example.litness.litness.Client;
import com.example.litness.litness.Bar.Review;
import com.example.litness.litness.R;

public class ReviewDialog extends AlertDialog {

    private final Context ctx;

    public ReviewDialog(Context c) {
        super(c);
        ctx = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_review);

        RecyclerView rv = findViewById(R.id.reviewdialog_rv);

        ReviewDialogAdapter adapter = new ReviewDialogAdapter(ctx);
        rv.setAdapter(adapter);

        adapter.updateReviews(Client.activeBar.reviews);

        findViewById(R.id.reviewdialog_button_add).setOnClickListener(x-> {
            dismiss();
            if(Client.currentUserName.equals("")) {
                new LoginDialog(ctx, x1 -> {
                    Client.currentUserName = x1;
                    new AddReviewDialog(ctx, "Add Review", r -> {
                        Review review = new Review();
                        review.text = r.get(0);
                        review.rating = r.get(1);
                        review.user = Client.currentUserName;
                        Client.activeBar.reviews.add(review);
                        new ReviewDialog(ctx).show();
                    }).show();
                }).show();
            }
            else {
                new AddReviewDialog(ctx, "Add Review", r -> {
                    Review review = new Review();
                    review.text = r.get(0);
                    review.rating = r.get(1);
                    review.user = Client.currentUserName;
                    Client.activeBar.reviews.add(review);
                    new ReviewDialog(ctx).show();
                }).show();
            }
        });

        findViewById(R.id.reviewdialog_button_close).setOnClickListener(x->
                dismiss());
    }
}

