package com.ayogeshwaran.bakingapp.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe mRecipe;

    private List<Ingredient> mIngredient;

    private List<Step> mStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipe = getIntent().getParcelableExtra(AppConstants.RECIPE_DETAIL_OBJECT);

        mIngredient = mRecipe.getIngredients();

        mStep = mRecipe.getSteps();

        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(mRecipe.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setRecipe(mRecipe);

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_list_container, recipeDetailsFragment)
                .commit();
    }
}
