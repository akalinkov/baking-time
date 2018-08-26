package com.example.android.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.fragments.IngredientsFragment;
import com.example.android.bakingtime.ui.fragments.StepDetailsFragment;
import com.example.android.bakingtime.ui.fragments.StepsFragment;
import com.example.android.bakingtime.ui.listeners.OnStepsItemClickListener;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements OnStepsItemClickListener {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private static final String RECIPE = "recipe_key";
    private static final String STEP_FRAGMENT_TAG = "steps_tag";
    private Recipe mRecipe = new Recipe();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: invoked");
        setContentView(R.layout.activity_recipe_details);

        if (null == savedInstanceState) {
            extractRecipeFromIntent(getIntent());
        } else {
            restoreInstanceState(savedInstanceState);
        }

        if (null != findViewById(R.id.step_details_container)) {
            mTwoPane = true;
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepsList(mRecipe.steps);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setIngredientsList(mRecipe.ingredients);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ingredients_container, ingredientsFragment)
                .commit();

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setStepsList(mRecipe.steps);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.steps_container, stepsFragment, STEP_FRAGMENT_TAG)
                .commit();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: save recipe state");
        outState.putParcelable(RECIPE, mRecipe);
    }

    @Override
    public void onStepClicked(int position) {
        if (mTwoPane) {
            Log.d(TAG, "onStepClicked: two panes - create new fragment");
            StepDetailsFragment newFragment = new StepDetailsFragment();
            newFragment.setStepsList(mRecipe.steps);
            newFragment.setCurrentStep(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_details_container, newFragment)
                    .commit();
        } else {
            Log.d(TAG, "onStepClicked: one pane - start new activity");
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(Step.SAVED_INTENT, (ArrayList<? extends Parcelable>) mRecipe.steps);
            intent.putExtra(Step.CURRENT, position);
            startActivity(intent);
        }
    }
}