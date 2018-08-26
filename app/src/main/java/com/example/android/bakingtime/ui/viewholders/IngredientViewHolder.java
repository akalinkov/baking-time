package com.example.android.bakingtime.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Ingredient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_ingredient)
    TextView mIngredient;

    @BindView(R.id.tv_quantity)
    TextView mQuantity;

    @BindView(R.id.tv_measure)
    TextView mMeasure;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(Ingredient ingredient) {
        mIngredient.setText(ingredient.name);
        mQuantity.setText(String.valueOf(ingredient.quantity));
        mMeasure.setText(ingredient.measure);
    }
}
