package com.ayogeshwaran.bakingapp.Ui;

import android.content.Intent;
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
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnStepClickListener, ExoPlayer.EventListener {

    private Recipe mRecipe;

    private List<Ingredient> mIngredient;

    private List<Step> mStep;

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipe = getIntent().getParcelableExtra(AppConstants.RECIPE_DETAIL_OBJECT);

        mIngredient = mRecipe.getIngredients();

        mStep = mRecipe.getSteps();

        initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportActionBar().setTitle(mRecipe.getName());

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
            recipeDetailsFragment.setRecipe(mRecipe);

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_details_list_container, recipeDetailsFragment)
                    .commit();
        }

        if (findViewById(R.id.video_fragment) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onStepSelected(Step step) {
        if (!mTwoPane) {
            Intent videoIntent = new Intent(this, VideoActivity.class);
            videoIntent.putExtra(AppConstants.STEP_OBJECT, step);
            startActivity(videoIntent);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setStep(step);


            fragmentManager.beginTransaction()
                    .replace(R.id.video_fragment, videoFragment)
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
