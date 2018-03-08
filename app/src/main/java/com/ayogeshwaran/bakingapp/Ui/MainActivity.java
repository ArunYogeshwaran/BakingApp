package com.ayogeshwaran.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.IdlingResource.SimpleIdlingResource;
import com.ayogeshwaran.bakingapp.Interfaces.IProgressListener;
import com.ayogeshwaran.bakingapp.R;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        RecipesFragment.OnRecipeClickListener, IProgressListener {

    private SimpleIdlingResource mSimpleIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onRecipeSelected(List<Recipe> recipes, int position) {
        Intent recipeDetailIntent = new Intent(this, RecipeDetailActivity.class);
        recipeDetailIntent.putExtra(AppConstants.RECIPE_DETAIL_OBJECT, recipes.get(position));
        startActivity(recipeDetailIntent);
    }

    @Override
    public void onStarted() {
        if (mSimpleIdlingResource != null) {
            mSimpleIdlingResource.setIdleState(false);
        }
    }

    @Override
    public void onDone() {
        if (mSimpleIdlingResource != null) {
            mSimpleIdlingResource.setIdleState(true);
        }
    }

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getSimpleIdlingResource() {
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }
}