package com.example.bar_buddy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class BarCardAdapter extends FirestoreRecyclerAdapter<BarItem, BarCardAdapter.BarViewHolder> {

    int mExpandedPosition = -1;
    int previousExpandedPosition = -1;

    Context ctx;

    public BarCardAdapter(@NonNull FirestoreRecyclerOptions<BarItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BarViewHolder holder, final int position, @NonNull BarItem model) {
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

        holder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(v.getContext(), BarMenu.class);
                ctx.startActivity(intent);
            }
        });

        holder.bar_name.setText(model.getBar_name());
        holder.cover.setText(model.getBar_cover());
        holder.wait_time.setText(model.getBar_wait_time_minutes());
        holder.description.setText(model.getBar_description());
    }

    @NonNull
    @Override
    public BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ctx = parent.getContext();

        View v = LayoutInflater.from(ctx).inflate(R.layout.adapter_bar_card, parent, false);
        return new BarViewHolder(v);
    }

    class BarViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardContainer;
        private final ConstraintLayout hiddenLayout;
        private final Button menuBtn;

        private final TextView bar_name;
        private final TextView cover;
        private final TextView wait_time;
        private final TextView description;

        public BarViewHolder(@NonNull View v) {
            super(v);

            bar_name = (TextView) itemView.findViewById(R.id.bar_name_tv);
            cover = (TextView) itemView.findViewById(R.id.cover_tv);
            wait_time = (TextView) itemView.findViewById(R.id.wait_time_tv);
            description = (TextView) itemView.findViewById(R.id.description_tv);

            menuBtn = (Button) itemView.findViewById(R.id.menu_btn);

            cardContainer = (CardView) itemView.findViewById(R.id.barcard_cv);
            hiddenLayout = (ConstraintLayout) itemView.findViewById(R.id.hiddenBarCardExpansion);
        }
    }
}

/*public class BarCardAdapter extends RecyclerView.Adapter<BarCardAdapter.BarViewHolder> {

    int mExpandedPosition = -1;
    int previousExpandedPosition = -1;

    private final Context ctx;
    private List<BarItem> data;

    class BarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardView cardContainer;
        private final ConstraintLayout hiddenLayout;
        
        private final TextView bar_name;
        private final TextView cover;
        private final TextView wait_time;
        private final TextView description;

        private final Button menuBtn;

        BarViewHolder(View v) {
            super(v);

            bar_name = (TextView) itemView.findViewById(R.id.bar_name_tv);
            cover = (TextView) itemView.findViewById(R.id.cover_tv);
            wait_time = (TextView) itemView.findViewById(R.id.wait_time_tv);
            description = (TextView) itemView.findViewById(R.id.description_tv);

            menuBtn = (Button) itemView.findViewById(R.id.menu_btn);

            cardContainer = (CardView) itemView.findViewById(R.id.barcard_cv);
            hiddenLayout = (ConstraintLayout) itemView.findViewById(R.id.hiddenBarCardExpansion);
            v.setClickable(true);
            v.setOnClickListener(this);
        }

        //on-click listener for clicking card anywhere except expand button
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            final Intent intent;
            intent = new Intent(v.getContext(), BarDisplay.class);
            //intent.putExtra("bar_id", )
            ctx.startActivity(intent);
        }
    }

    public BarCardAdapter(Context c, List<BarItem> data) {
        this.ctx = c;
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

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

        holder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(v.getContext(), BarMenu.class);
                ctx.startActivity(intent);
            }
        });

        holder.bar_name.setText(data.get(position).bar_name);
        holder.cover.setText(data.get(position).bar_cover);
        holder.wait_time.setText(data.get(position).bar_wait_time_minutes);
        holder.description.setText(data.get(position).uid);
    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}*/
