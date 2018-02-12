package com.ayogeshwaran.bakingapp.Db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface IngredientDao {
    @Query("select * from recipe")
    List<Recipe> getAllRecipes();

    @Insert(onConflict = IGNORE)
    void insertIngredient(Ingredient ingredient);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceIngredients(Ingredient... ingredients);

//    @Query("SELECT * FROM Ingredient " +
//            "INNER JOIN Ingredient ON Ingredient.recipe_id = :id")
//    public List<Ingredient> getIngredientsForRecipe(Integer id);
}
