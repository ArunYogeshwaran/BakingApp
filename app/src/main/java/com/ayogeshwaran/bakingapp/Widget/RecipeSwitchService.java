package com.ayogeshwaran.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.R;

public class RecipeSwitchService extends IntentService {

    public RecipeSwitchService() {
        super("RecipeSwitchService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int recipeId = 1;
        if (intent != null) {
            if (intent.hasExtra(AppConstants.RECIPE_ID)) {
                recipeId = intent.getIntExtra(AppConstants.RECIPE_ID, 1);
            }
        }
        getIngredientsForRecipeId(recipeId);
    }

    private void getIngredientsForRecipeId(int recipeId) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                R.layout.recipe_widget_list_item);

        remoteViews.setTextViewText(R.id.widget_ingredient_name_text_view, "Changed Ingredient");
        remoteViews.setTextViewText(R.id._widget_ingredient_quantity_text_view, "600UNITS");


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                                RecipeWidgetProvider.class));
        //Trigger data update to handle the ListView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        //Now update all widgets
        RecipeWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds);
    }
}
