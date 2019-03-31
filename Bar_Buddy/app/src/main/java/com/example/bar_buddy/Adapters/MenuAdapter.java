package com.example.bar_buddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bar_buddy.AdapterItems.MenuItem;
import com.example.bar_buddy.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final Context ctx;
    private List<MenuItem> data;

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardContainer;

        private final TextView itemName;
        private final TextView itemPrice;
        private final TextView itemDescription;

        MenuViewHolder(View v) {
            super(v);

            cardContainer = (CardView) itemView.findViewById(R.id.menucard_cv);

            itemName = (TextView) v.findViewById(R.id.menu_item_name);
            itemPrice = (TextView) v.findViewById(R.id.menu_item_price);
            itemDescription = (TextView) v.findViewById(R.id.menu_item_description);
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
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        String price = "$" + data.get(position).getItem_price();

        holder.itemName.setText(data.get(position).getItem_name());
        holder.itemPrice.setText(price);
        holder.itemDescription.setText(data.get(position).getItem_description());
    }

    @Override
    public int getItemCount() {
        if(data == null || data.isEmpty()) return 0;
        return data.size();
    }
}
