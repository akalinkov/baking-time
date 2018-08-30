package com.example.android.bakingtime.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.fragments.StepDetailsFragment;
import com.example.android.bakingtime.utils.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

    private static final String STEPS_LIST = "steps_list_key";
    private static final String CURRENT_STEP = "current_step_key";
    private static final String STEP_FRAGMENT_TAG = "step_fragment_tag";

    private StepDetailsFragment mDetailsFragment;
    private List<Step> mStepsList;
    private int currentStep;
    private boolean autoPlay;
    private long playbackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if (null == savedInstanceState) {
            getExtras();
            addDetailsFragment();
        } else {
            restoreSavedState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: invoked");
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEPS_LIST, (ArrayList<? extends Parcelable>) mStepsList);
        outState.putInt(CURRENT_STEP, currentStep);
    }

    private void restoreSavedState(@NonNull Bundle inState) {
        Log.d(TAG, "restoreSavedState: invoked");
        mStepsList = inState.getParcelableArrayList(STEPS_LIST);
        currentStep = inState.getInt(CURRENT_STEP);
    }

    private void addDetailsFragment() {
        Log.d(TAG, "addDetailsFragment: invoked");
        mDetailsFragment = new StepDetailsFragment();
        mDetailsFragment.setStepsList(mStepsList);
        mDetailsFragment.setCurrentStep(currentStep);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_details_container, mDetailsFragment, STEP_FRAGMENT_TAG)
                .commit();
    }

    private void getExtras() {
        Log.d(TAG, "getExtras: invoked");
        if (getIntent().hasExtra(Step.SAVED_INTENT)) {
            mStepsList = getIntent().getParcelableArrayListExtra(Step.SAVED_INTENT);
        }
        if (null == mStepsList) {
            mStepsList = Arrays.asList(new Step());
        }
        if (getIntent().hasExtra(Step.CURRENT)) {
            currentStep = getIntent().getIntExtra(Step.CURRENT, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
