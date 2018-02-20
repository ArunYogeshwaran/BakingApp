package com.ayogeshwaran.bakingapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferenceUtils {
    public static Recipe getRecipeFromPreferences(Context context, int position) {
        String jsonFromPreferences = getSharedPreferences(context)
                .getString(AppConstants.RECIPE_WIDGET_INFO_KEY, "");

        Type collectionType = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> recipes = new Gson().fromJson(jsonFromPreferences, collectionType);

        if (recipes != null) {
            return recipes.get(position);
        } else {
            return new Recipe();
        }
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences
                (AppConstants.RECIPE_WIDGET_PREFERENCE_FILE, Context.MODE_PRIVATE);

        return preferences;
    }
}
