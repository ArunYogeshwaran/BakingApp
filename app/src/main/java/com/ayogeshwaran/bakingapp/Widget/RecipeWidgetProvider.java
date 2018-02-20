package com.ayogeshwaran.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.MainActivity;
import com.ayogeshwaran.bakingapp.Utils.SharedPreferenceUtils;

public class RecipeWidgetProvider extends AppWidgetProvider {
    private static Recipe currentRecipe;

    private static SharedPreferences mPrefs;

    private static final String PREVIOUS_CLICKED = "previous_clicked";

    private static final String NEXT_CLICKED = "next_clicked";

    private RemoteViews remoteViews;

    public RecipeWidgetProvider() {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                  int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
        RemoteViews rv = getRecipeRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private RemoteViews getRecipeRemoteView(Context context) {
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_view);

        mPrefs = SharedPreferenceUtils.getSharedPreferences(context);
        int position = mPrefs.getInt(AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY,
                AppConstants.RECIPE_WIDGET_CURRENT_ITEM_MIN);
        currentRecipe = SharedPreferenceUtils.getRecipeFromPreferences(context, position - 1);

        remoteViews.setTextViewText(R.id.widget_title_text, currentRecipe.getName());
        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0,
                appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_title_text, appPendingIntent);

        remoteViews.setOnClickPendingIntent(R.id.widget_recipe_before,
                getPendingSelfIntent(context, PREVIOUS_CLICKED));
        remoteViews.setOnClickPendingIntent(R.id.widget_recipe_after,
                getPendingSelfIntent(context, NEXT_CLICKED));

        Intent recipeWidgetServiceIntent = new Intent(context, RecipeWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.widget_list_view, recipeWidgetServiceIntent);

        return remoteViews;
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        int currentPosition = SharedPreferenceUtils.getSharedPreferences(context).getInt
                (AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY,
                        AppConstants.RECIPE_WIDGET_CURRENT_ITEM_MIN);

        SharedPreferences.Editor prefsEditor = SharedPreferenceUtils
                .getSharedPreferences(context).edit();
        if (PREVIOUS_CLICKED.equals(intent.getAction())) {
            if (currentPosition != 1) {
                prefsEditor.putInt(AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY, currentPosition - 1);
            } else {
                prefsEditor.putInt(AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY,
                        AppConstants.RECIPE_WIDGET_CURRENT_ITEM_MAX);
            }
        } else if (NEXT_CLICKED.equals(intent.getAction())) {
            if (currentPosition != 4) {
                prefsEditor.putInt(AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY, currentPosition + 1);
            } else {
                prefsEditor.putInt(AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY,
                        AppConstants.RECIPE_WIDGET_CURRENT_ITEM_MIN);
            }
        }
        prefsEditor.apply();
        prefsEditor.commit();

        int position = SharedPreferenceUtils.getSharedPreferences(context).getInt
                        (AppConstants.RECIPE_WIDGET_CURRENT_ITEM_KEY,
                        AppConstants.RECIPE_WIDGET_CURRENT_ITEM_MIN);

        currentRecipe = SharedPreferenceUtils.getRecipeFromPreferences(context,position - 1);

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_view);

        remoteViews.setTextViewText(R.id.widget_title_text, currentRecipe.getName());

        Intent recipeWidgetServiceIntent = new Intent(context, RecipeWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.widget_list_view, recipeWidgetServiceIntent);

        notifyDataChanged(context);
    }

    private void notifyDataChanged(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,
                RecipeWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

        updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }
}
