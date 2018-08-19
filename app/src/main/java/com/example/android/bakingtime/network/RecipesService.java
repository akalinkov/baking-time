package com.example.android.bakingtime.network;

import com.example.android.bakingtime.model.Recipe;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
