package com.ayogeshwaran.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.Adapters.RecipeDetailsPagerAdapter;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.List;

import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity
        implements StepsFragment.OnStepClickListener, ExoPlayer.EventListener {

    private Recipe mRecipe;

    private Step mCurrentStep;

    private boolean mTwoPane = false;

    private ViewPager viewPager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipe = getIntent().getParcelableExtra(AppConstants.RECIPE_DETAIL_OBJECT);

        initViews(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(AppConstants.STEP_OBJECT, mCurrentStep);
    }

    private void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        viewPager = findViewById(R.id.recipe_detail_viewpager);
        TabLayout tabLayout = findViewById(R.id.recipe_detail_tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setTitle(mRecipe.getName());

        if (findViewById(R.id.video_container_second_pane) != null) {
            mTwoPane = true;

            FragmentManager fragmentManager = getSupportFragmentManager();
            VideoFragment videoFragment = new VideoFragment();
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(AppConstants.STEP_OBJECT)) {
                    mCurrentStep = savedInstanceState.getParcelable(AppConstants.STEP_OBJECT);
                    videoFragment.setStep(mCurrentStep);
                }
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container_second_pane, videoFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipe(mRecipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_list_container, ingredientsFragment)
                .commit();
    }

    private void setupViewPager() {
        RecipeDetailsPagerAdapter recipeDetailsPagerAdapter = new RecipeDetailsPagerAdapter(getSupportFragmentManager());

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipe(mRecipe);
        recipeDetailsPagerAdapter.addFragment(ingredientsFragment, getString(R.string.ingredients),
                mRecipe);

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setRecipe(mRecipe);
        recipeDetailsPagerAdapter.addFragment(stepsFragment, getString(R.string.steps),
                mRecipe);

        this.viewPager.setAdapter(recipeDetailsPagerAdapter);
    }

    @Override
    public void onStepSelected(Step step) {
        if (!mTwoPane) {
            Intent videoIntent = new Intent(this, VideoActivity.class);
            videoIntent.putExtra(AppConstants.STEP_OBJECT, step);
            startActivity(videoIntent);
        } else {
            mCurrentStep = step;

            FragmentManager fragmentManager = getSupportFragmentManager();
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setStep(step);

            fragmentManager.beginTransaction()
                    .replace(R.id.video_container_second_pane, videoFragment)
                    .commit();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
