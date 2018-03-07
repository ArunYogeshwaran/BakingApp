package com.ayogeshwaran.bakingapp.Ui.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Ui.IngredientsFragment;
import com.ayogeshwaran.bakingapp.Ui.StepsFragment;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();

    private final List<String> mFragmentTitles = new ArrayList<>();

    private Recipe mRecipe;

    public RecipeDetailsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title, Recipe recipe) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mRecipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setRecipe(mRecipe);
                return ingredientsFragment;
            case 1:
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setRecipe(mRecipe);
                return stepsFragment;
            default:
                IngredientsFragment ingredientsFragmentDefault = new IngredientsFragment();
                ingredientsFragmentDefault.setRecipe(mRecipe);
                return ingredientsFragmentDefault;
        }
    }


    @Override
    public String getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
