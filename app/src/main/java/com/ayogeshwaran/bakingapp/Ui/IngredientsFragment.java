package com.ayogeshwaran.bakingapp.Ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.Adapters.IngredientListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredient_recycler_view)
    private RecyclerView ingredientRecyclerView;

    private Recipe mRecipe;

    private final String INGREDIENTS_RV_POSITION = "ingredients_postion";

    private Bundle mSavedInstanceState;


    public IngredientsFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(INGREDIENTS_RV_POSITION,
                ingredientRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container,
                false);

        setRetainInstance(true);

        ButterKnife.bind(this, rootView);

        initViews();

        if(savedInstanceState != null){
            // scroll to existing position which exist before rotation.
            mSavedInstanceState = savedInstanceState;
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setRecipe(Recipe recipe) {
        if (recipe != null) {
            mRecipe = recipe;
            List<Ingredient> mIngredients = recipe.getIngredients();
        }
    }

    private void initViews() {
        buildIngredientList();
    }

    private void buildIngredientList() {
        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), 1, false);

        ingredientRecyclerView.setLayoutManager(linearLayoutManager);

        ingredientRecyclerView.setHasFixedSize(false);

        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(getContext(), mRecipe);

        ingredientRecyclerView.setAdapter(ingredientListAdapter);

        ingredientListAdapter.updateIngredients(mRecipe);
        if (mSavedInstanceState != null) {
            ingredientRecyclerView.getLayoutManager().onRestoreInstanceState(
                    mSavedInstanceState.getParcelable(INGREDIENTS_RV_POSITION));
        }
    }
}
