package com.example.bar_buddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BarCardAdapter extends RecyclerView.Adapter<BarCardAdapter.BarViewHolder> {

    int mExpandedPosition = -1;
    int previousExpandedPosition = -1;

    private final Context ctx;
    private List<Bar> data;

    class BarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardView cardContainer;
        private final TextView barText;
        //private final TextView hiddenTextView;
        private final ConstraintLayout hiddenLayout;

        BarViewHolder(View v) {
            super(v);
            cardContainer = (CardView) itemView.findViewById(R.id.barcard_cv);
            barText = (TextView) itemView.findViewById(R.id.bar_text_view_example);
            //hiddenTextView = (TextView) itemView.findViewById(R.id.hiddenTextView);
            hiddenLayout = (ConstraintLayout) itemView.findViewById(R.id.hiddenBarCardExpansion);
            v.setClickable(true);
            v.setOnClickListener(this);
        }

        //on-click listener for clicking card anywhere except expand button
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
    public void onBindViewHolder(@NonNull final BarViewHolder holder, final int position) {
        final boolean isExpanded = position==mExpandedPosition;
        final Button expand_button = holder.itemView.findViewById(R.id.expand_button);

        holder.hiddenLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if(isExpanded) {
            previousExpandedPosition = position;
            expand_button.setBackground(ActivityCompat.getDrawable(ctx, R.drawable.ic_expand_less));
        } else {
            expand_button.setBackground(ActivityCompat.getDrawable(ctx, R.drawable.ic_expand_more));
        }

        //on-click listener for expand button click
        expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                if(isExpanded) {
                    expand_button.setBackground(ActivityCompat.getDrawable(ctx, R.drawable.ic_expand_less));
                } else {
                    expand_button.setBackground(ActivityCompat.getDrawable(ctx, R.drawable.ic_expand_more));
                }

                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });

        holder.barText.setText(data.get(position).text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
