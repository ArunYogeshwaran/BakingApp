package com.ayogeshwaran.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Utils.SharedPreferenceUtils;

import java.util.List;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(getApplicationContext());
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;

    private List<Ingredient> mIngredients;

    private Recipe mRecipe;

    public RecipeRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        int currentPosition = SharedPreferenceUtils.getSharedPreferences(mContext).getInt(
                AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY, 1);
        mRecipe = SharedPreferenceUtils.getRecipeFromPreferences(
                                   mContext,currentPosition - 1);
        mIngredients = mRecipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.recipe_widget_list_item);

        remoteViews.setTextViewText(R.id.widget_ingredient_name_text_view,
                mIngredients.get(position).getIngredient());

        StringBuilder sb = new StringBuilder().append(mIngredients.get(position).getQuantity())
                                    .append(mIngredients.get(position).getMeasure());
        remoteViews.setTextViewText(R.id._widget_ingredient_quantity_text_view, sb.toString());

        return remoteViews;
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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
