package com.example.android.bakingtime;

import com.example.android.bakingtime.model.Recipe;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Temp {

    private String JSON;

    @Before
    public void readJson() throws IOException {
        JSON = getFile();
    }


    @Test
    public void recipeModelTest() throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        Type recipesListType = Types.newParameterizedType(List.class, Recipe.class);
        JsonAdapter<List<Recipe>> jsonAdapter = moshi.adapter(recipesListType);
        List<Recipe> recipes = jsonAdapter.fromJson(JSON);
        System.out.println("# = " + recipes.size());
    }

    private String getFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("data.json"));
    }
}
