package com.ayogeshwaran.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.MainActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews rv = getRecipeRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getRecipeRemoteView(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.recipe_widget_list_view);

        views.setTextViewText(R.id.widget_text, "Nutella pie");

        Intent intent = new Intent(context, RecipeWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0,
                                                    appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_text, appPendingIntent);

        return views;

    }
}
