package com.example.android.bakingtime.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.ui.listeners.OnRecipeClickListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = RecipeViewHolder.class.getSimpleName();
    private OnRecipeClickListener mItemClickListener;

    @BindView(R.id.iv_recipe_icon)
    ImageView mImage;

    @BindView(R.id.tv_recipe_name)
    TextView mName;

    @BindView(R.id.tv_servings)
    TextView mServings;

    public RecipeViewHolder(View itemView,
                            OnRecipeClickListener itemClickListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Recipe recipe) {
        Log.d(TAG, "bind: recipe = " + recipe.name);
        mName.setText(recipe.name);
        mServings.setText(recipe.servings + " servings");
        if (null != recipe.image && !"".equals(recipe.image)) {
            // TODO: convert image url into URI. Handle exceptions.
            Picasso.get().load("http://clipart-library.com/images/5cR485Eca.jpg").into(mImage);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: #" + getAdapterPosition());
        mItemClickListener.onRecipeClicked(getAdapterPosition());
    }
}
