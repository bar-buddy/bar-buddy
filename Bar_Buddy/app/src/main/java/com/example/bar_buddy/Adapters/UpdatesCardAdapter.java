package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.R;
import com.example.bar_buddy.AdapterItems.UpdateItem;
import com.example.bar_buddy.TabFragments.UpdatesTab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UpdatesCardAdapter extends RecyclerView.Adapter<UpdatesCardAdapter.UpdatesCardViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final Context ctx;
    private List<UpdateItem> data;
    
    class UpdatesCardViewHolder extends RecyclerView.ViewHolder {
        
        private final CardView cardContainer;

        private final ImageView bar_image;

        private final TextView bar_name;
        private final TextView update_title;
        private final TextView update_description;
        private final TextView update_start_time;
        
        UpdatesCardViewHolder(View v) {
            super(v);
            
            cardContainer = (CardView) itemView.findViewById(R.id.updatecard_cv);
            bar_image = (ImageView) itemView.findViewById(R.id.updates_card_image);
            bar_name = (TextView) itemView.findViewById(R.id.update_card_barname);
            update_title = (TextView) itemView.findViewById(R.id.update_card_title);
            update_description = (TextView) itemView.findViewById(R.id.update_card_description);
            update_start_time = (TextView) itemView.findViewById(R.id.update_card_start_time);
        }
    }

    public UpdatesCardAdapter(Context c, final List<UpdateItem> data) {
        this.ctx = c;
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public UpdatesCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_update_card, parent, false);
        return new UpdatesCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UpdatesCardViewHolder holder, final int position) {
        holder.bar_name.setText(data.get(position).getBar_name());
        holder.update_title.setText(data.get(position).getUpdate_title());
        holder.update_description.setText(data.get(position).getUpdate_description());

        String start = "Starts " + data.get(position).getUpdate_start_time();
        holder.update_start_time.setText(start);

        String image = data.get(position).getUpdate_image();
        if(image != null && !image.equals("")) {
            Picasso.get()
                    .load(image)
                    .into(holder.bar_image);
        }

        /*BarItem b = data.get(position).getBar();
        if(b != null) {
            holder.bar_name.setText(b.getBar_name());
            Picasso.get()
                    .load(b.getBar_image())
                    .placeholder(R.drawable.loading_image)
                    //.error(R.drawable.no_image_available)
                    .into(holder.bar_image);
        }*/
    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}
