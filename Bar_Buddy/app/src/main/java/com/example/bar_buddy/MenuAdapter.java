package com.example.bar_buddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final Context ctx;
    private List<MenuItem> data;

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardContainer;

        MenuViewHolder(View v) {
            super(v);

            cardContainer = (CardView) itemView.findViewById(R.id.menucard_cv);
        }
    }

    public MenuAdapter(Context c, List<MenuItem> data) {
        this.ctx = c;
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new MenuViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_menu_card, viewGroup, false));
        View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_menu_card, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder menuViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}
