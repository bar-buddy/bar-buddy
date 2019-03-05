package com.example.bar_buddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BarCardAdapter extends RecyclerView.Adapter<BarCardAdapter.BarViewHolder> {

    private final Context ctx;
    private List<Bar> data;

    class BarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardView cardContainer;
        private final TextView barText;

        BarViewHolder(View v) {
            super(v);
            cardContainer = (CardView) itemView.findViewById(R.id.barcard_cv);
            barText = (TextView) itemView.findViewById(R.id.bar_text_view_example);
        }

        @Override
        public void onClick(View v) {
            final Intent intent;
            intent = new Intent(v.getContext(), BarDisplay.class);
            ctx.startActivity(intent);
            //startActivity(new Intent(this, BarDisplayPage.class))
        }
    }

    public BarCardAdapter(Context c, List<Bar> data) {
            this.ctx = c;
            this.data = data;
    }

    /*public void updateBars(Collection<Bar> c) {
            data = new ArrayList<>(c);
            notifyDataSetChanged();
    }*/

    /*@Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }*/

    @NonNull
    @Override
    public BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BarViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_bar_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BarViewHolder holder, int position) {
        holder.barText.setText(data.get(position).text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
