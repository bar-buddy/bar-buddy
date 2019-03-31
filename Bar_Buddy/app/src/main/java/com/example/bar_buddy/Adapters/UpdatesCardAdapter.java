package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bar_buddy.R;
import com.example.bar_buddy.AdapterItems.UpdateItem;

import java.util.List;

public class UpdatesCardAdapter extends RecyclerView.Adapter<UpdatesCardAdapter.UpdatesCardViewHolder> {
    
    private final Context ctx;
    private List<UpdateItem> data;
    
    class UpdatesCardViewHolder extends RecyclerView.ViewHolder {
        
        private final CardView cardContainer;
        
        UpdatesCardViewHolder(View v) {
            super(v);
            
            cardContainer = (CardView) itemView.findViewById(R.id.updatecard_cv);
        }
    }
    
    public UpdatesCardAdapter(Context c, List<UpdateItem> data) {
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
    public void onBindViewHolder(@NonNull UpdatesCardViewHolder updatesCardViewHolder, int i) {
        
    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}
