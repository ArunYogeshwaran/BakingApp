package com.ayogeshwaran.bakingapp.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
    @BindView(R.id.playerView)
    public SimpleExoPlayerView playerView;

    @BindView(R.id.video_description_container)
    public TextView videoDecriptiontextView;

    @BindView(R.id.no_video_textview)
    public TextView noVideotextView;

    private Step mStep = new Step();

    private SimpleExoPlayer mExoPlayer;

    private ExoPlayer.EventListener mCallback;

    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    private static final String TEST_URL = "https://www.youtube.com/watch?v=kxyAc_CP3EI&t=460s";

    private static final int TEXT_VIEW_ID = 33;

    private long playbackPosition = 0;

    private int currentWindow = 0;

    private boolean playWhenReady = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        ButterKnife.bind(this, rootView);

        initViews();

        return rootView;
    }

    private void initViews() {
        videoDecriptiontextView.setText(mStep.getDescription());

        playerView.hideController();
        playerView.setControllerVisibilityListener(new PlaybackControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if (visibility == 0) {
                    playerView.showController();
                } else {
                    playerView.hideController();
                }
            }
        });
        initializePlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (ExoPlayer.EventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    public void setStep(Step step) {
        mStep = step;
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);

            // Create an instance of the ExoPlayer.
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), loadControl);
            showPlayerView();
            playerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(0);
            mExoPlayer.addListener(mCallback);

            Uri uri = Uri.parse("");
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                uri = Uri.parse(mStep.getVideoURL());
            } else {
                showNoVideoView();
            }

            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource, true, false);
        }
    }

    private void showPlayerView() {
        playerView.setVisibility(View.VISIBLE);
        noVideotextView.setVisibility(View.INVISIBLE);
    }

    private void showNoVideoView() {
        noVideotextView.setVisibility(View.VISIBLE);
        playerView.setVisibility(View.INVISIBLE);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("exoplayer"),
                new DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

//    INTERFACE
    public interface IOnVideoTapped {
        void videoTapped(int state);
    }
}

