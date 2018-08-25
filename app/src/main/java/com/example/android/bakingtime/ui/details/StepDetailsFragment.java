package com.example.android.bakingtime.ui.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public StepDetailsFragment() { }

    private SimpleExoPlayer mPlayer;
    private Step mStep;

    private TextView mStepTitle;
    private TextView mDescription;
    private PlayerView mPlayerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        mPlayerView = rootView.findViewById(R.id.player);
        mDescription = rootView.findViewById(R.id.description);
        mStepTitle = rootView.findViewById(R.id.step_title);
        initializePlayer();
        mPlayerView.setPlayer(mPlayer);

        bindData();
        setVideoVisibility();

        return rootView;
    }

    public void setStep(Step step) {
        mStep = step;
    }

    public void changeStep(Step step) {
        setStep(step);
        bindData();
        setVideoVisibility();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void setVideoVisibility() {
        mPlayerView.setUseController(!mStep.videoURL.isEmpty());
    }

    private void bindData() {
        mStepTitle.setText(mStep.shortDescription);
        mDescription.setText(mStep.description);
        preparePlayer();
    }

    private Uri videoUri() {
        return Uri.parse(mStep.videoURL);
    }

    private void preparePlayer() {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "baking_time"),
                new DefaultBandwidthMeter());
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri());
        mPlayer.prepare(videoSource);
    }


    private void initializePlayer() {
        TrackSelection.Factory trackSelectorFactory = new AdaptiveTrackSelection
                .Factory(new DefaultBandwidthMeter());
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(trackSelectorFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
    }

    private void releasePlayer() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
}
