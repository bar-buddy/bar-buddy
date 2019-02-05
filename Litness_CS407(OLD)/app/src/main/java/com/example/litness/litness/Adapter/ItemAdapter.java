package com.example.litness.litness.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.litness.litness.Bar.Item;
import com.example.litness.litness.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context ctx;
    private List<Item> data = new ArrayList<>();

    public ItemAdapter(Context c) {
        this.ctx = c;
    }

    public void updateItems(Collection<Item> c) {
        data = new ArrayList<>(c);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(ctx).inflate(R.layout.adapter_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item i = data.get(position);
        holder.tvText.setText(i.item);
        holder.tvPrice.setText(Integer.toString(i.price));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvPrice;
        private final TextView tvText;

        ItemViewHolder(View v) {
            super(v);
            tvPrice = v.findViewById(R.id.itemcard_alt_price);
            tvText = v.findViewById(R.id.itemcard_alt_text);
        }
    }

}
