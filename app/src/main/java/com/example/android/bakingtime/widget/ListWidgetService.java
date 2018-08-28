package com.example.android.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Ingredient;
import com.example.android.bakingtime.ui.PreferencesHelper;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {

    private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: invoked");
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private List<Ingredient> mIngredients;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    private void bind(RemoteViews views, int position) {
        Ingredient ingredient = mIngredients.get(position);
        Log.d(TAG, "bind: ingredient " + ingredient.name);
        views.setTextViewText(R.id.tv_quantity, String.valueOf(ingredient.quantity));
        views.setTextViewText(R.id.tv_measure, ingredient.measure);
        views.setTextViewText(R.id.tv_ingredient, ingredient.name);
    }

    @Override
    public void onCreate() { }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: invoked");
        mIngredients = PreferencesHelper.getIngredients(mContext);
    }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: invoked");
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: position #" + position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_ingredient);
        bind(views, position);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}