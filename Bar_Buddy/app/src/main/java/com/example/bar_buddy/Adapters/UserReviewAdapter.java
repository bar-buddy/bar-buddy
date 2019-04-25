package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.UserReviewItem;
import com.example.bar_buddy.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.UserReViewHolder> {

    private final Context ctx;
    private List<UserReviewItem> data;

    class UserReViewHolder extends RecyclerView.ViewHolder {

        private final TextView user_cover;
        private final TextView user_wait;
        private final TextView user_time_submitted;

        UserReViewHolder(@NonNull View itemView) {
            super(itemView);

            user_cover = (TextView) itemView.findViewById(R.id.user_cover);
            user_wait = (TextView) itemView.findViewById(R.id.user_wait);
            user_time_submitted = (TextView) itemView.findViewById(R.id.user_time_submitted);
        }
    }

    public UserReviewAdapter(Context c, List<UserReviewItem> data) {
        this.ctx = c;
        this.data = data;
    }

    @NonNull
    @Override
    public UserReViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserReViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_user_review_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserReViewHolder holder, final int position) {
        String coverString = "Cover: $" + data.get(position).getBar_cover();
        String waitString = "Wait: " + data.get(position).getBar_wait() + " minutes";
        holder.user_cover.setText(coverString);
        holder.user_wait.setText(waitString);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String s = simpleDateFormat.format(data.get(position).getTime_submitted());

        holder.user_time_submitted.setText(s);
    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}
