package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.WeeklySpecialItem;
import com.example.bar_buddy.R;

import org.w3c.dom.Text;

import java.util.List;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.WeeklyViewHolder> {

    private Context ctx;
    private List<WeeklySpecialItem> weeklySpecials;

    public WeeklyAdapter(Context c, List<WeeklySpecialItem> weeklySpecials) {
        this.ctx = c;
        this.weeklySpecials = weeklySpecials;
    }

    class WeeklyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description, day;

        WeeklyViewHolder(View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.weekly_card_title);
            description = (TextView) v.findViewById(R.id.weekly_card_description);
            day = (TextView) v.findViewById(R.id.weekly_card_day);
        }
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new WeeklyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_weekly_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position) {
        holder.title.setText(weeklySpecials.get(position).getSpecial_name());
        holder.description.setText(weeklySpecials.get(position).getSpecial_description());
        holder.day.setText(weeklySpecials.get(position).getSpecial_day());
    }

    @Override
    public int getItemCount() {
        if(weeklySpecials == null || weeklySpecials.isEmpty()) return 0;
        return weeklySpecials.size();
    }
}
