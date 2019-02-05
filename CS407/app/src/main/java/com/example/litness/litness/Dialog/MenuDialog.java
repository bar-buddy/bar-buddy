package com.example.litness.litness.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.litness.litness.Adapter.ItemAdapter;
import com.example.litness.litness.Bar;
import com.example.litness.litness.Client;
import com.example.litness.litness.R;

public class MenuDialog extends AlertDialog {

    private final Context ctx;

    public MenuDialog(Context c) {
        super(c);
        ctx = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu);

        RecyclerView rvDrink = findViewById(R.id.menudialog_drink_rv);
        RecyclerView rvFood = findViewById(R.id.menudialog_food_rv);

        Bar b = Client.activeBar;
        if(b.menu.drinks.size() != 0) {
            ItemAdapter drinkAdapter = new ItemAdapter(ctx);
            rvDrink.setAdapter(drinkAdapter);
            drinkAdapter.updateItems(b.menu.drinks);
        }
        else {
            findViewById(R.id.menudialog_label_drinks).setVisibility(View.GONE);
            rvDrink.setVisibility(View.GONE);
        }

        if(b.menu.food.size() != 0) {
            ItemAdapter foodAdapter = new ItemAdapter(ctx);
            rvFood.setAdapter(foodAdapter);
            foodAdapter.updateItems(b.menu.food);
        }
        else {
            findViewById(R.id.menudialog_label_food).setVisibility(View.GONE);
            rvFood.setVisibility(View.GONE);
        }

        findViewById(R.id.menudialog_button_close).setOnClickListener(x->
                dismiss());
    }
}
