package com.ayogeshwaran.bakingapp.Ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.ExoplayerVideoHandler;
import com.ayogeshwaran.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
    @BindView(R.id.playerView)
    public SimpleExoPlayerView playerView;

    @BindView(R.id.video_description_container)
    public TextView videoDecriptiontextView;

    @BindView(R.id.no_video_textview)
    public TextView noVideotextView;

    @BindView(R.id.video_loading_indicator)
    public ProgressBar videoLoadingProgressbar;

    @BindView(R.id.video_container)
    public FrameLayout videoContainer;

    private Step mStep = new Step();

    private SimpleExoPlayer mExoPlayer;

    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    private long playbackPosition = C.TIME_UNSET;

    private int currentWindow = 0;

    private boolean playWhenReady = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        setRetainInstance(true);

        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(AppConstants.STEP_OBJECT)) {
                mStep = savedInstanceState.getParcelable(AppConstants.STEP_OBJECT);
            }
        }

        initViews();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(AppConstants.STEP_OBJECT, mStep);
    }


    private void initViews() {
        if (mStep != null) {
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
        }

        initializePlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            ExoPlayer.EventListener mCallback = (ExoPlayer.EventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoplayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ExoplayerVideoHandler.getInstance().releaseVideoPlayer();
    }

    public void setStep(Step step) {
        mStep = step;
    }

    private void initializePlayer() {
        if (mStep != null) {
            if (TextUtils.isEmpty(mStep.getVideoURL())) {
                showNoVideoView();
            } else {
                showPlayerView();
                String videoUrl = mStep.getVideoURL();
                if(videoUrl != null && playerView != null) {
                    ExoplayerVideoHandler.getInstance()
                            .prepareExoPlayerForUri(getContext(),
                                    Uri.parse(videoUrl), playerView);
                    ExoplayerVideoHandler.getInstance().goToForeground();
                }
            }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.video_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.go_to_home:
                Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

