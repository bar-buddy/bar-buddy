package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.EventItem;
import com.example.bar_buddy.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context ctx;
    private List<EventItem> events;

    public EventAdapter(Context c, List<EventItem> events) {
        this.ctx = c;
        this.events = events;
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView date;

        EventViewHolder(View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.event_card_title);
            description = (TextView) v.findViewById(R.id.event_card_description);
            date = (TextView) v.findViewById(R.id.event_card_date);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_event_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.title.setText(events.get(position).getEvent_name());
        holder.description.setText(events.get(position).getEvent_description());
        holder.date.setText(events.get(position).getEvent_date());
    }

    @Override
    public int getItemCount() {
        if(events == null || events.isEmpty()) return 0;
        return events.size();
    }
}
