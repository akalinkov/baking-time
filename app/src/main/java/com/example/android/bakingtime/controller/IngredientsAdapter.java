package com.example.android.bakingtime.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Ingredient;
import com.example.android.bakingtime.ui.IngredientViewHolder;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private Context mContext;
    private List<Ingredient> mIngredientsList;

    public IngredientsAdapter(Context context, @NonNull List<Ingredient> ingredientsList) {
        mContext = context;
        mIngredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bindData(mIngredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }
}
