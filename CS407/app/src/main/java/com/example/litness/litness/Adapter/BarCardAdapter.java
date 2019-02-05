package com.example.litness.litness.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.litness.litness.Bar;
import com.example.litness.litness.Bar.Day;
import com.example.litness.litness.BarDisplayActivity;
import com.example.litness.litness.Client;
import com.example.litness.litness.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BarCardAdapter extends RecyclerView.Adapter<BarCardAdapter.BarViewHolder> {

    private final Context ctx;
    private List<Bar> data;

    public BarCardAdapter(Context c) {
        ctx = c;
        data = new ArrayList<>();
    }

    public void updateBars(Collection<Bar> c) {
        data = new ArrayList<>(c);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BarViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_bar_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BarViewHolder holder, int position) {
        Bar b = data.get(position);

        holder.tagTainer.removeAllViews();
        holder.tvNone.setVisibility(View.GONE);
        if(b.days != null) {
            boolean printNone = true;
            for(int i = 0; i < b.days.size(); i++) {
                Day d = b.days.get(i);
                if(d.day.equals(String.format("%s", android.text.format.DateFormat.format("EEEE", new Date())))) {
                    printNone = false;
                    for (String cat : d.events) {
                        @SuppressLint("InflateParams") View v = LayoutInflater.from(ctx).inflate(R.layout.adapter_events, null, false);
                        ((TextView) v.findViewById(R.id.adapter_alt_event)).setText(cat);
                        holder.tagTainer.addView(v);
                    }
                    for (String cat : d.specials) {
                        @SuppressLint("InflateParams") View v = LayoutInflater.from(ctx).inflate(R.layout.adapter_specials, null, false);
                        ((TextView) v.findViewById(R.id.adapter_alt_special)).setText(cat);
                        holder.tagTainer.addView(v);
                    }
                }
            }
            if (printNone)
                holder.tvNone.setVisibility(View.VISIBLE);
        }
        //make the specials invisible if there are none
        else {
            holder.tagTainer.setVisibility(View.GONE);
        }

        holder.tvBarName.setText(b.barName);
        holder.tvWaitTime.setText(b.wait);

        //don't show under cover if there isn't one
        if(!b.coverUnder.equals("$0"))
            holder.tvCover.setText(b.coverOver + " | " + b.coverUnder);
        else
            holder.tvCover.setText(b.coverOver);

        if(b.coverOver.equals("$0")) {
            holder.tvCover.setText("No Cover");
            holder.tvCover.setTextColor(ContextCompat.getColor(ctx,(R.color.HelperTextTransparent)));
        }
        else
            holder.tvCover.setTextColor(ContextCompat.getColor(ctx,(R.color.HelperText)));

        if(b.wait.equals("")) {
            holder.tvWaitTime.setTextColor(ContextCompat.getColor(ctx,(R.color.HelperTextTransparent)));
            holder.tvWaitTime.setText("No Wait");
        }
        else
            holder.tvWaitTime.setTextColor(ContextCompat.getColor(ctx,(R.color.HelperText)));

        switch (b.litness) {
            case "1":
                holder.imgLit.setImageResource(R.drawable.meter_1);
                break;
            case "2":
                holder.imgLit.setImageResource(R.drawable.meter_2);
                break;
            case "3":
                holder.imgLit.setImageResource(R.drawable.meter_3);
                break;
            case "4":
                holder.imgLit.setImageResource(R.drawable.meter_4);
                break;
            default:
                holder.imgLit.setImageResource(R.drawable.meter_5);
                break;
        }

        holder.cardContainer.setOnClickListener(v -> {
            //get the bar clicked on
            Client.activeBar = data.get(holder.getAdapterPosition());
            ctx.startActivity(new Intent(ctx, BarDisplayActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BarViewHolder extends RecyclerView.ViewHolder{

        final LinearLayout tagTainer;
        private final CardView cardContainer;
        private final TextView tvBarName;
        private final TextView tvWaitTime;
        private final TextView tvCover;
        private final TextView tvNone;
        private final ImageView imgLit;

        BarViewHolder(View v) {
            super(v);
            tagTainer = v.findViewById(R.id.barcard_container_tags);
            cardContainer = itemView.findViewById(R.id.barcard_cv);
            tvBarName = itemView.findViewById(R.id.barcard_alt_name);
            tvWaitTime = itemView.findViewById(R.id.barcard_alt_wait);
            tvCover = itemView.findViewById(R.id.barcard_alt_cover);
            tvNone = itemView.findViewById(R.id.barcard_no_events);
            imgLit = itemView.findViewById(R.id.barcard_img_litness);
        }
    }

}
