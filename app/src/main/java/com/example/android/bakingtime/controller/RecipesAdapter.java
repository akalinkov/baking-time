package com.example.android.bakingtime.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.ui.RecipeViewHolder;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();
    private List<Recipe> mRecipes;
    public RecipeItemClickListener mRecipeItemClickListener;

    public RecipesAdapter(@NonNull List<Recipe> recipes,
                          @NonNull RecipeItemClickListener recipeItemClickListener) {
        this.mRecipes = recipes;
        this.mRecipeItemClickListener = recipeItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(recipeView, mRecipeItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void replaceRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        Log.d(TAG, "replaceRecipes: notify data set changed");
        notifyDataSetChanged();
    }

    public interface RecipeItemClickListener {
        void onRecipeClick(int position);
    }
}
