package com.example.litness.litness.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.litness.litness.Bar.Day;
import com.example.litness.litness.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DayDialogAdapter extends RecyclerView.Adapter<DayDialogAdapter.CardDialogViewHolder> {

    private final Context ctx;
    private List<Day> data = new ArrayList<>();

    public DayDialogAdapter(Context c) {
        ctx = c;
    }

    public void updateDays(Collection<Day> c) {
        data = new ArrayList<>(c);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardDialogViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_day_dialog,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardDialogViewHolder holder, int position) {
        Day d = data.get(position);
        holder.tvTitle.setText(d.day);
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (int i=0; i<d.events.size(); i++) {
            sb.append(prefix);
            prefix = ", ";
            //makes the last thing have an and
            if (i == d.events.size() - 2)
                prefix = ", and ";
            if (d.events.size() == 2)
                prefix = " and ";
            sb.append(d.events.get(i));
        }
        holder.tvText1.setText(sb);

        //Change from events to event
        if(d.events.size() == 1)
            holder.tvLabel1.setText("Event");

        //if there are no events hide them
        if(d.events.size() == 0) {
            holder.tvLabel1.setVisibility(View.GONE);
            holder.tvText1.setVisibility(View.GONE);
        }

        sb = new StringBuilder();
        prefix = "";
        for (int i=0; i<d.specials.size(); i++) {
            sb.append(prefix);
            prefix = ", ";
            //makes the last thing have an and
            if (i == d.specials.size() - 2)
                prefix = ", and ";
            if (d.specials.size() == 2)
                prefix = " and ";
            sb.append(d.specials.get(i));
        }
        holder.tvText2.setText(sb);

        //Change from specials to specials if there's one
        if(d.specials.size() == 1)
            holder.tvLabel2.setText("Special");

        //if there are no specials hide them
        if(d.specials.size() == 0) {
            holder.tvLabel2.setVisibility(View.GONE);
            holder.tvText2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CardDialogViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvTitle;
        private final TextView tvLabel1;
        private final TextView tvLabel2;
        private final TextView tvText1;
        private final TextView tvText2;

        CardDialogViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.daycard_alt_title);
            tvLabel1 = v.findViewById(R.id.daycard_label_1);
            tvLabel2 = v.findViewById(R.id.daycard_label_2);
            tvText1 = v.findViewById(R.id.daycard_alt_1);
            tvText2 = v.findViewById(R.id.daycard_alt_2);
        }
    }

}
