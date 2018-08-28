package com.example.android.bakingtime.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingtime.model.Ingredient;
import com.example.android.bakingtime.model.Recipe;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PreferencesHelper {

    private static final String TAG = PreferencesHelper.class.getSimpleName();

    private static final String INGREDIENTS_JSON_KEY = "ingredients_json_key";
    private static final String RECIPE_NAME_KEY = "recipe_name_key";

    private static String toJson(List<Ingredient> ingredients) {
        Log.d(TAG, "toJson: ingredients #" + ingredients.size());
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Ingredient.class);
        JsonAdapter<List<Ingredient>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.toJson(ingredients);
    }

    @Nullable
    private static List<Ingredient> fromJson(String json) {
        Log.d(TAG, "fromJson: " + json);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Ingredient.class);
        JsonAdapter<List<Ingredient>> jsonAdapter = moshi.adapter(type);
        List<Ingredient> ingredients = null;
        try {
            ingredients = jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public static void saveIngredients(Context context, Recipe recipe) {
        Log.d(TAG, "saveIngredients: recipe " + recipe.name);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INGREDIENTS_JSON_KEY, toJson(recipe.ingredients));
        editor.putString(RECIPE_NAME_KEY, recipe.name);
        editor.apply();
    }

    @Nullable
    public static List<Ingredient> getIngredients(Context context) {
        Log.d(TAG, "getIngredients: invoked");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPrefs.getString(INGREDIENTS_JSON_KEY, null);
        return fromJson(json);
    }

    @Nullable
    public static String getRecipeName(Context context) {
        Log.d(TAG, "getRecipeName: invoked");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(RECIPE_NAME_KEY, null);
    }

}
