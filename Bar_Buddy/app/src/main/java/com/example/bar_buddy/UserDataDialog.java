package com.example.bar_buddy;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.AdapterItems.UserReviewItem;
import com.example.bar_buddy.Adapters.UserReviewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDataDialog extends AlertDialog {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final Context ctx;

    List<UserReviewItem> reviews;

    private UserReviewAdapter adapter;

    private BarItem bar;


    public UserDataDialog(Context ctx, BarItem b) {
        super(ctx);
        this.bar = b;
        this.ctx = ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user_data);
        Objects.requireNonNull(getWindow()).clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        TextView title = (TextView) findViewById(R.id.title_user_data);
        String titleText = "Cover and Wait";
        title.setText(titleText);

        String barCover = "Cover: $" + bar.getBar_cover();
        String barWait = "Wait time: " + bar.getBar_wait() + " mins";
        TextView barCoverTv = (TextView) findViewById(R.id.user_data_cover);
        TextView barWaitTv = (TextView) findViewById(R.id.user_data_wait);
        final TextView avgCover = (TextView) findViewById(R.id.avg_cover);
        final TextView avgWait = (TextView) findViewById(R.id.avg_wait);
        barCoverTv.setText(barCover);
        barWaitTv.setText(barWait);

        RecyclerView rvCards = (RecyclerView) findViewById(R.id.user_reviews_dialog_recyclerview);
        rvCards.setLayoutManager(new LinearLayoutManager(ctx));

        reviews = new ArrayList<UserReviewItem>();

        adapter = new UserReviewAdapter(ctx, reviews);
        rvCards.setAdapter(adapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        readData(new FirestoreCallback() {
            @Override
            public void onCallback(int user_cover, int user_wait) {
                adapter.notifyDataSetChanged();
                String avgCov = "Average Cover: $" + user_cover;
                String avgWt = "Average Wait time: " + user_wait + " mins";
                avgCover.setText(avgCov);
                avgWait.setText(avgWt);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private interface FirestoreCallback {
        void onCallback(int user_cover, int user_wait);
    }

    private void readData(final FirestoreCallback firestoreCallback) {
         db.collection("bars").document(bar.getBar_id()).collection("user_reviews")
                 .orderBy("time_submitted", Query.Direction.DESCENDING)
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             int user_cover = 0, user_wait = 0, user_count = 0;

                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 UserReviewItem review = document.toObject(UserReviewItem.class);

                                 if(reviews != null) {
                                     reviews.add(review);
                                 }
                                 user_cover += Integer.parseInt(review.getBar_cover());
                                 user_wait += Integer.parseInt(review.getBar_wait());
                                 user_count++;
                             }
                             if(user_count > 0) {
                                 user_cover /= user_count;
                                 user_wait /= user_count;
                             } else if(user_count == 0) {
                                 user_cover = -1;
                                 user_wait = -1;
                             }
                             firestoreCallback.onCallback(user_cover, user_wait);
                         }
                     }
                 });
    }
}


