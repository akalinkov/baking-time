package com.example.android.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.controller.StepsListAdapter;
import com.example.android.bakingtime.model.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private Recipe mRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        if (null == savedInstanceState) {
            extractRecipeFromIntent(getIntent());
        } else {
            Log.d(TAG, "onCreate: saved instance state is not null");
        }

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setIngredientsList(mRecipe.ingredients);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ingredients_container, ingredientsFragment)
                .commit();

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setStepsList(mRecipe.steps);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.steps_container, stepsFragment)
                .commit();
    }

    private void extractRecipeFromIntent(Intent intent) {
        Log.d(TAG, "extractRecipeFromIntent: ");
        if (intent.hasExtra(Recipe.CLICKED)) {
            mRecipe = intent.getParcelableExtra(Recipe.CLICKED);
        }
    }
}