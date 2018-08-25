package com.example.android.bakingtime.ui.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailsFragment extends Fragment {

    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private static final String PLAYER_POSITION = "player_position_key";
    private static final String STEP = "step_key";

    private SimpleExoPlayer mPlayer;
    private long mPlayerPosition;
    private Step mStep;

    private TextView mStepTitle;
    private TextView mDescription;
    private PlayerView mPlayerView;

    public StepDetailsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: invoked");
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        findViews(rootView);

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }

        bindData();
        return rootView;
    }

    public void setStep(@NonNull Step step) {
        Log.d(TAG, "setStep: #" + step.id);
        mStep = step;
    }

    public void changeStep(@NonNull Step step) {
        Log.d(TAG, "changeStep: invoked");
        setStep(step);
        bindData();
        preparePlayer();
    }

    private void findViews(View rootView) {
        Log.d(TAG, "findViews: invoked");
        mPlayerView = rootView.findViewById(R.id.player);
        mDescription = rootView.findViewById(R.id.description);
        mStepTitle = rootView.findViewById(R.id.step_title);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: invoked");
        initializePlayer();
        preparePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: invoked");
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: invoked");
        outState.putLong(PLAYER_POSITION, null == mPlayer ? 0 : mPlayer.getCurrentPosition());
        outState.putParcelable(STEP, mStep);
    }

    private void restoreInstanceState(@NonNull Bundle inState) {
        Log.d(TAG, "restoreInstanceState: invoked");
        mPlayerPosition = inState.getLong(PLAYER_POSITION, 0);
        mStep = inState.getParcelable(STEP);
    }

    private void setPlayerVisibility(boolean isPlayerVisible) {
        if (isPlayerVisible) {
            Log.d(TAG, "updateVideoVisibility: visibility = VISIBLE");
            mPlayerView.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "updateVideoVisibility: visibility = GONE");
            mPlayerView.setVisibility(View.GONE);
        }
    }

    private void bindData() {
        Log.d(TAG, "bindData: invoked");
        mStepTitle.setText(mStep.shortDescription);
        mDescription.setText(mStep.description);
    }

    private Uri videoUri() {
        Log.d(TAG, "videoUri: parse video URL = " + mStep.videoURL);
        return Uri.parse(mStep.videoURL);
    }

    private void preparePlayer() {
        Log.d(TAG, "preparePlayer: invoked");
        if (null == mStep.videoURL || mStep.videoURL.isEmpty()) {
            Log.d(TAG, "preparePlayer: video url is empty - stop method execution");
            setPlayerVisibility(false);
            return;
        }
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "baking_time"),
                new DefaultBandwidthMeter());
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri());
        mPlayer.prepare(videoSource);
        setPlayerVisibility(true);
    }

    private void initializePlayer() {
        Log.d(TAG, "initializePlayer: invoked");
        TrackSelection.Factory trackSelectorFactory = new AdaptiveTrackSelection
                .Factory(new DefaultBandwidthMeter());
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(trackSelectorFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mPlayerView.setPlayer(mPlayer);
    }

    private void releasePlayer() {
        Log.d(TAG, "releasePlayer: stop and release player");
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
}
