package com.example.android.bakingtime.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.controller.RecipesAdapter;
import com.example.android.bakingtime.idlingResource.SimpleIdlingResource;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.network.RecipesDownloader;
import com.example.android.bakingtime.network.RecipesDownloaderCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements RecipesDownloaderCallback, OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_VIEW_WIDTH = 800;
    private RecipesAdapter mRecipesAdapter;
    public List<Recipe> mRecipes = new ArrayList<>();

    @BindView(R.id.rv_recipes_list)
    RecyclerView mRecipesRecyclerView;

    @Nullable
    private IdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: setup recycler view");
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount());
        mRecipesRecyclerView.setLayoutManager(layoutManager);
        mRecipesAdapter = new RecipesAdapter(mRecipes, this);
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);

        RecipesDownloader.requestRecipes(this, null);
    }

    @Override
    public void onDone(@NonNull List<Recipe> recipes) {
        Log.d(TAG, "onDone: ");
        mRecipes = recipes;
        mRecipesAdapter.replaceRecipes(recipes);
    }

    @Override
    public void onFail() {
        Toast.makeText(this, "Download failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "onRecipeClick: #" + position);
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Recipe.CLICKED, mRecipes.get(position));
        startActivity(intent);
    }

    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if (null == mIdlingResource) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private int spanCount() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        return width / RECIPE_VIEW_WIDTH;
    }
}
