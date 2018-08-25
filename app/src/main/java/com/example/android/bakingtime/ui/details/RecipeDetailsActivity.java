package com.example.android.bakingtime.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;

import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private static final String RECIPE = "recipe_key";
    private Recipe mRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: invoked");
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        if (null == savedInstanceState) {
            extractRecipeFromIntent(getIntent());
        } else {
            restoreInstanceState(savedInstanceState);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: save recipe state");
        outState.putParcelable(RECIPE, mRecipe);
    }

    private void extractRecipeFromIntent(Intent intent) {
        Log.d(TAG, "extractRecipeFromIntent: invoked");
        if (intent.hasExtra(Recipe.CLICKED)) {
            mRecipe = intent.getParcelableExtra(Recipe.CLICKED);
        }
    }

    private void restoreInstanceState(@NonNull Bundle inState) {
        Log.d(TAG, "restoreInstanceState: recipe");
        mRecipe = inState.getParcelable(RECIPE);
    }
}