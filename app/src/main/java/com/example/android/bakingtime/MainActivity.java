package com.example.android.bakingtime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingtime.idlingResource.SimpleIdlingResource;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.network.RecipesDownloader;
import com.example.android.bakingtime.network.RecipesDownloaderCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipesDownloaderCallback {

    public List<Recipe> mRecipes;

    @Nullable
    private IdlingResource mIdlingResource;
    @BindView(R.id.hello_world)
    TextView hello_world;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecipesDownloader.requestRecipes(this, null);
    }

    @Override
    public void onDone(@NonNull List<Recipe> recipes) {
        mRecipes = recipes;
    }

    @Override
    public void onFail() {
        Toast.makeText(this, "Download failed", Toast.LENGTH_LONG).show();
    }

    @VisibleForTesting
    public IdlingResource getmIdlingResource() {
        if (null == mIdlingResource) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
