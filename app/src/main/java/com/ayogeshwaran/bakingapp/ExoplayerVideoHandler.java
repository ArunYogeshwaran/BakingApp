package com.ayogeshwaran.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ExoplayerVideoHandler
{
    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    private static ExoplayerVideoHandler instance;

    private SimpleExoPlayer mExoPlayer;

    public static ExoplayerVideoHandler getInstance(){
        if(instance == null){
            instance = new ExoplayerVideoHandler();
        }
        return instance;
    }

    private Uri playerUri;

    private boolean isPlayerPlaying;

    private ExoplayerVideoHandler(){}

    public void prepareExoPlayerForUri(Context context, Uri uri,
                                       SimpleExoPlayerView exoPlayerView){
        if(context != null && uri != null && exoPlayerView != null){
            if(!uri.equals(playerUri) || mExoPlayer == null){
                // Create a new player if the player is null or
                // we want to play a new video
                playerUri = uri;
                // Prepare the player with the source.
                TrackSelection.Factory adaptiveTrackSelectionFactory =
                        new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);

                // Create an instance of the ExoPlayer.
                LoadControl loadControl = new DefaultLoadControl();

                mExoPlayer = ExoPlayerFactory.newSimpleInstance(context,
                        new DefaultTrackSelector(adaptiveTrackSelectionFactory), loadControl);
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(context,
                        new DefaultTrackSelector(adaptiveTrackSelectionFactory), loadControl);
                exoPlayerView.setPlayer(mExoPlayer);


                mExoPlayer.setPlayWhenReady(true);

                MediaSource mediaSource = buildMediaSource(uri);
                mExoPlayer.prepare(mediaSource);
            }
            mExoPlayer.clearVideoSurface();
            mExoPlayer.setVideoSurfaceView(
                    (SurfaceView)exoPlayerView.getVideoSurfaceView());
            mExoPlayer.seekTo(mExoPlayer.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(mExoPlayer);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("exoplayer"),
                new DefaultExtractorsFactory(), null, null);
    }

    public void releaseVideoPlayer(){
        if(mExoPlayer != null)
        {
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    public void goToBackground(){
        if(mExoPlayer != null){
            isPlayerPlaying = mExoPlayer.getPlayWhenReady();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    public void goToForeground(){
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(isPlayerPlaying);
        }
    }
}