package com.example.android.bakingtime.ui.details;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.controller.IngredientsAdapter;
import com.example.android.bakingtime.model.Ingredient;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private List<Ingredient> mIngredientsList;

    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        RecyclerView ingredientsView = rootView.findViewById(R.id.gv_ingredients_list);
        IngredientsAdapter adapter = new IngredientsAdapter(getContext(), mIngredientsList);
        ingredientsView.setAdapter(adapter);

        return rootView;
    }

    public void setIngredientsList(List<Ingredient> ingredients) {
        mIngredientsList = ingredients;
    }
}
