package com.example.android.bakingtime.network;

import android.support.annotation.NonNull;

import com.example.android.bakingtime.model.Recipe;

import java.util.List;

public interface RecipesDownloaderCallback {
    void onDone(@NonNull List<Recipe> recipes);
    void onFail();
}
