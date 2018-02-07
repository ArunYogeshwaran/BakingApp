package com.ayogeshwaran.bakingapp.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.Interfaces.IOnItemClickedListener;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.Adapters.IngredientListAdapter;
import com.ayogeshwaran.bakingapp.Ui.Adapters.StepsListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements IOnItemClickedListener {

    @BindView(R.id.ingredient_recycler_view)
    public RecyclerView ingredientRecyclerView;

    @BindView(R.id.steps_recycler_view)
    public RecyclerView stepsRecyclerView;

    private IngredientListAdapter ingredientListAdapter;

    private StepsListAdapter stepsListAdapter;

    private Recipe mRecipe;

    private List<Ingredient> mIngredients;

    private List<Step> mSteps;

    public RecipeDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container,
                false);

        ButterKnife.bind(this, rootView);

        initViews();

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        if (recipe != null) {
            mRecipe = recipe;
            mIngredients = recipe.getIngredients();
            mSteps = recipe.getSteps();
        }
    }

    private void initViews() {
        buildIngredientFragment();
        buildStepsFragment();
    }

    private void buildIngredientFragment() {
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(), 1);

        ingredientRecyclerView.setLayoutManager(gridLayoutManager);

        ingredientRecyclerView.setHasFixedSize(true);

        ingredientListAdapter = new IngredientListAdapter(getContext());
        ingredientListAdapter.updateIngredients(mIngredients);

        ingredientRecyclerView.setAdapter(ingredientListAdapter);
    }

    private void buildStepsFragment() {
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(), 1);

        stepsRecyclerView.setLayoutManager(gridLayoutManager);

        stepsRecyclerView.setHasFixedSize(true);

        stepsListAdapter = new StepsListAdapter(getContext(), this);
        stepsListAdapter.updateSteps(mSteps);

        stepsRecyclerView.setAdapter(stepsListAdapter);
    }

    @Override
    public void OnItemClicked(int position) {
        Step step = mSteps.get(position);


    }
}
