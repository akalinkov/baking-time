package com.example.android.bakingtime.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.utils.Device;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

public class StepDetailsFragment extends Fragment {

    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private static final String PLAYER_POSITION = "player_position_key";
    private static final String STEPS_LIST = "steps_list_key";
    private static final String CURRENT_STEP = "current_step_key";
    private static final String AUTO_PLAY_KEY = "auto_play_key";
    private static final String CURRENT_WINDOW_KEY = "current_window_key";

    private List<Step> mStepsList;
    private int mCurrentStep;

    private long mPlaybackPosition;
    private boolean mAutoPlay;
    private int mCurrentWindow;

    private SimpleExoPlayer mPlayer;

    @BindView(R.id.btn_prev)
    Button mPrevious;

    @BindView(R.id.btn_next)
    Button mNext;

    @BindView(R.id.navigation)
    LinearLayout mNavigation;

    @BindView(R.id.step_title)
    TextView mStepTitle;

    @BindView(R.id.description)
    TextView mDescription;

    @BindView(R.id.player)
    PlayerView mPlayerView;

    private Unbinder unbinder;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: invoked");
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }

        bindData();
        updateNavigationVisibility();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setCurrentStep(int currentStep) {
        Log.d(TAG, "setCurrentStep: #" + currentStep);
        mCurrentStep = currentStep;
    }

    public void setStepsList(@NonNull List<Step> steps) {
        Log.d(TAG, "setStepsList: invoked");
        mStepsList = steps;
    }

    private Step getStep() {
        return mStepsList.get(mCurrentStep);
    }

    private void changeStep(@NonNull int stepIndex) {
        Log.d(TAG, "changeStep: invoked");
        mPlayer.stop();
        setCurrentStep(stepIndex);
        updateNavigationVisibility();
        bindData();
        preparePlayer();
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
        outState.putLong(PLAYER_POSITION, mPlaybackPosition);
        outState.putParcelableArrayList(STEPS_LIST, (ArrayList<? extends Parcelable>) mStepsList);
        outState.putInt(CURRENT_STEP, mCurrentStep);
        outState.putBoolean(AUTO_PLAY_KEY, mAutoPlay);
        outState.putInt(CURRENT_WINDOW_KEY, mCurrentWindow);
    }

    private void restoreInstanceState(@NonNull Bundle inState) {
        Log.d(TAG, "restoreInstanceState: invoked");
        mStepsList = inState.getParcelableArrayList(STEPS_LIST);
        mCurrentStep = inState.getInt(CURRENT_STEP);
        mPlaybackPosition = inState.getLong(PLAYER_POSITION);
        mAutoPlay = inState.getBoolean(AUTO_PLAY_KEY);
        mCurrentWindow = inState.getInt(CURRENT_WINDOW_KEY);
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
        if (Device.isLandscape(getContext())
                && !Device.isTablet(getContext())
                && !getStep().videoURL.isEmpty()) {
            Log.d(TAG, "bindData: set title and description invisible");
            mStepTitle.setVisibility(View.GONE);
            mDescription.setVisibility(View.GONE);
            return;
        }
        Log.d(TAG, "bindData: set title and description invisible");
        mStepTitle.setVisibility(View.VISIBLE);
        mDescription.setVisibility(View.VISIBLE);
        mStepTitle.setText(getStep().shortDescription);
        mDescription.setText(getStep().description);
    }

    private Uri videoUri() {
        Log.d(TAG, "videoUri: parse video URL = " + getStep().videoURL);
        return Uri.parse(getStep().videoURL);
    }

    private void preparePlayer() {
        Log.d(TAG, "preparePlayer: invoked");
        if (null == getStep().videoURL || getStep().videoURL.isEmpty()) {
            Log.d(TAG, "preparePlayer: video url is empty - stop method execution");
            setPlayerVisibility(false);
            return;
        }
        setPlayerVisibility(true);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "baking_time"),
                new DefaultBandwidthMeter());
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri());
        mPlayer.setPlayWhenReady(mAutoPlay);
        mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        mPlayer.prepare(videoSource);
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
        mPlaybackPosition = mPlayer.getCurrentPosition();
        mAutoPlay = mPlayer.getPlayWhenReady();
        mCurrentWindow = mPlayer.getCurrentWindowIndex();
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    private boolean shouldNavigationBeVisible() {
        if (Device.isTablet(getContext())) return false;
        if (Device.isLandscape(getContext())
                && !getStep().videoURL.isEmpty()) {
            return false;
        }
        return true;
    }

    private void updateNavigationVisibility() {
        if (!shouldNavigationBeVisible()) {
            mNavigation.setVisibility(View.GONE);
            return;
        }
        mNavigation.setVisibility(View.VISIBLE);
        mPrevious.setEnabled(mCurrentStep != 0);
        mNext.setEnabled(mStepsList.size() - 1 != mCurrentStep);
    }

    @Optional
    @OnClick(R.id.btn_prev)
    public void previousStep() {
        changeStep(--mCurrentStep);
    }

    @Optional
    @OnClick(R.id.btn_next)
    public void nextStep() {
        changeStep(++mCurrentStep);
    }
}
