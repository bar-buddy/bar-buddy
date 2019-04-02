package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.bar_buddy.Activities.BarDisplay;
import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.Activities.BarMenu;
import com.example.bar_buddy.ButtonRangeExtender;
import com.example.bar_buddy.R;
import com.example.bar_buddy.TabFragments.HomeTab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.bar_buddy.HandleBarsThroughFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BarCardAdapter extends RecyclerView.Adapter<BarCardAdapter.BarViewHolder> {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    private final Context ctx;
    private List<BarItem> data;

    class BarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardView cardContainer;
        private final ConstraintLayout hiddenLayout;
        
        private final TextView bar_name;
        private final TextView cover;
        private final TextView wait_time;
        private final TextView hours_operation;
        private final TextView description;

        private final ImageView image;

        private final Button expandBtn;
        private final ToggleButton favBtn;
        private final ImageButton directionsBtn;
        private final Button menuBtn;

        BarViewHolder(View v) {
            super(v);

            bar_name = (TextView) itemView.findViewById(R.id.bar_name_tv);
            cover = (TextView) itemView.findViewById(R.id.cover_tv);
            wait_time = (TextView) itemView.findViewById(R.id.wait_time_tv);
            hours_operation = (TextView) itemView.findViewById(R.id.hours_operation_tv);
            description = (TextView) itemView.findViewById(R.id.description_tv);

            image = (ImageView) itemView.findViewById(R.id.bar_imageview);

            expandBtn = (Button) itemView.findViewById(R.id.expand_button);
            favBtn = (ToggleButton) itemView.findViewById(R.id.bar_card_favorite_tglBtn);
            directionsBtn = (ImageButton) itemView.findViewById(R.id.bar_card_directions_btn);
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
            intent.putExtra("bar_id", data.get(position).getBar_id());
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

        setBtnListeners(holder, position);

        String wait = "Wait time: " + data.get(position).getBar_wait() + " minutes";
        String cover = "Cover: $" + data.get(position).getBar_cover();
        String description = "Description: " + data.get(position).getBar_description();
        String hours = "Hours of operation: " + data.get(position).getBar_hours_operation();

        holder.bar_name.setText(data.get(position).getBar_name());
        holder.cover.setText(cover);
        holder.wait_time.setText(wait);
        holder.hours_operation.setText(hours);
        holder.description.setText(description);

        Picasso.get()
                .load(data.get(position).getBar_image())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.no_image_available)
                .into(holder.image);
    }

    private interface FavCheckCallback {
        void onCallback(boolean result);
    }

    private void checkFavorite(final FavCheckCallback callback, CollectionReference ref, final String bar_id) {
        ref.whereEqualTo("bar_id", bar_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean result = false;
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                result = true;
                                Log.e(bar_id, (String) document.get("bar_id"));
                            }


                        }
                        callback.onCallback(result);
                    }
                });
    }

    private void setBtnListeners(final BarViewHolder holder, final int position) {
        final boolean isExpanded = position==mExpandedPosition;
        final Button expand_button = holder.expandBtn;

        //expand button hit areas
        new ButtonRangeExtender(expand_button, 100, 100, 100, 100);
        new ButtonRangeExtender(holder.favBtn, 100, 0, 100, 100);
        new ButtonRangeExtender(holder.directionsBtn, 100, 100, 100, 0);

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
                intent.putExtra("bar_id", data.get(position).getBar_id());
                ctx.startActivity(intent);
            }
        });

        boolean initial = true;
        //boolean result = new HandleBarsThroughFirestore().isFavorite(data.get(position).getBar_id(), holder.favBtn);
        final CollectionReference favRef = db.collection("users").document(user.getUid()).collection("bars_favorites");
        checkFavorite(new FavCheckCallback() {
            @Override
            public void onCallback(boolean result) {
                holder.favBtn.setChecked(result);

                holder.favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            new HandleBarsThroughFirestore().addFavorite(data.get(position).getBar_id());
                        } else {
                            new HandleBarsThroughFirestore().removeFavorite(data.get(position).getBar_id());
                        }
                    }
                });
            }
        }, favRef, data.get(position).getBar_id());

        holder.directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=" + Uri.encode(data.get(position).getBar_name() + " " + data.get(position).getBar_address()))));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}
