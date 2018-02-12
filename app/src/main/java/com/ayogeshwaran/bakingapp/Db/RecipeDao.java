package com.ayogeshwaran.bakingapp.Db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ayogeshwaran.bakingapp.Data.Model.Recipe;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("select * from recipe")
    List<Recipe> getAllRecipes();

    @Insert(onConflict = REPLACE)
    void insertRecipe(Recipe recipe);

    @Insert(onConflict = REPLACE)
    void insertOrReplaceRecipes(Recipe... recipes);
}
