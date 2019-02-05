package com.example.litness.litness.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.litness.litness.Bar.Review;
import com.example.litness.litness.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewDialogAdapter extends RecyclerView.Adapter<ReviewDialogAdapter.ReviewDialogViewHolder> {

    private final Context ctx;
    private List<Review> data = new ArrayList<>();

    public ReviewDialogAdapter(Context c) {
        ctx = c;
    }

    public void updateReviews(Collection<Review> c) {
        data = new ArrayList<>(c);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewDialogViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_review_dialog,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewDialogViewHolder holder, int position) {
        Review r = data.get(position);
        holder.tvRating.setText(r.rating);
        holder.tvText.setText(r.text);
        holder.tvUser.setText(r.user);
        holder.tvDate.setText(new SimpleDateFormat("MM/dd/yy", Locale.US).format(new Date(r.timestamp)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ReviewDialogViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvRating;
        private final TextView tvDate;
        private final TextView tvText;
        private final TextView tvUser;

        ReviewDialogViewHolder(View v) {
            super(v);
            tvRating = v.findViewById(R.id.reviewcard_alt_rating);
            tvText = v.findViewById(R.id.reviewcard_alt_text);
            tvUser = v.findViewById(R.id.reviewcard_alt_user);
            tvDate = v.findViewById(R.id.reviewcard_date);
        }
    }

}
