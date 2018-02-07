package com.ayogeshwaran.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.R;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        RecipesFragment.OnRecipeClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    @Override
    public void onRecipeSelected(List<Recipe> recipes, int position) {
        Intent recipeDetailIntent = new Intent(this, RecipeDetailActivity.class);
        recipeDetailIntent.putExtra(AppConstants.RECIPE_DETAIL_OBJECT, recipes.get(position));
        startActivity(recipeDetailIntent);
    }
}