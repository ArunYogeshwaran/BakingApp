package com.ayogeshwaran.bakingapp.Db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;

@Database(entities = {Recipe.class, Ingredient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract RecipeDao recipeModel();

    public abstract IngredientDao ingredientModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
