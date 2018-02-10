package com.ayogeshwaran.bakingapp.Ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity implements ExoPlayer.EventListener {
    private Step mStep = new Step();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            if (getIntent().hasExtra(AppConstants.STEP_OBJECT)) {
                mStep = getIntent().getParcelableExtra(AppConstants.STEP_OBJECT);
            }
        }

        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(mStep.getShortDescription());

        FragmentManager fragmentManager = getSupportFragmentManager();
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setStep(mStep);

        fragmentManager.beginTransaction()
                .add(R.id.video_fragment, videoFragment)
                .commit();
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
