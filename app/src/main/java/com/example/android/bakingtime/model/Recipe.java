package com.example.android.bakingtime.model;


import com.squareup.moshi.Json;

import org.json.JSONObject;

import java.util.List;

public class Recipe {

    public int id;
    public String name;
    public List<Ingredient> ingredients = null;
    public List<Step> steps = null;
    public Integer servings;
    public String image;
}
