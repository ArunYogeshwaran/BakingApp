package com.ayogeshwaran.bakingapp.Ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private OnStepClickListener mCallback;

    @BindView(R.id.ingredient_recycler_view)
    public RecyclerView ingredientRecyclerView;

    @BindView(R.id.steps_recycler_view)
    public RecyclerView stepsRecyclerView;

    private IngredientListAdapter ingredientListAdapter;

    private StepsListAdapter stepsListAdapter;

    private List<Step> mSteps;


    public RecipeDetailsFragment() {

    }

    public interface OnStepClickListener {
        void onStepSelected(Step step);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String STEPS_RV_POSITION = "steps_postion";
        outState.putParcelable(STEPS_RV_POSITION,
                stepsRecyclerView.getLayoutManager().onSaveInstanceState());
        String INGREDIENTS_RV_POSITION = "ingredients_postion";
        outState.putParcelable(INGREDIENTS_RV_POSITION,
                ingredientRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container,
                false);

        setRetainInstance(true);

        ButterKnife.bind(this, rootView);

        initViews();

        if(savedInstanceState != null){
            // scroll to existing position which exist before rotation.
            Bundle mSavedInstanceState = savedInstanceState;
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public void setRecipe(Recipe recipe) {
        if (recipe != null) {
            Recipe mRecipe = recipe;
            List<Ingredient> mIngredients = recipe.getIngredients();
            mSteps = recipe.getSteps();
        }
    }

    private void initViews() {
//        buildIngredientList();
//        buildStepsList();
    }

    @Override
    public void OnItemClicked(int position) {
        Step step = mSteps.get(position);

        mCallback.onStepSelected(step);
    }

    public static class RecipeDetailsPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public RecipeDetailsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                case 1:

                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public String getPageTitle(int position) {
            switch (position) {
                case 0:
                    return String.valueOf(R.string.ingredients);

                case 1:
                    return String.valueOf(R.string.steps);

                default:
                    return null;
            }
        }

    }
}
