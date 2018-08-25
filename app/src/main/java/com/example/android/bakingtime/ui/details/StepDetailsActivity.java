package com.example.android.bakingtime.ui.details;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String STEPS_LIST = "steps_list_key";
    private static final String CURRENT_STEP = "current_step_key";
    @BindView(R.id.btn_prev)
    Button mPrevious;
    @BindView(R.id.btn_next)
    Button mNext;

    private StepDetailsFragment mDetailsFragment;
    private List<Step> mStepsList;
    private int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ButterKnife.bind(this);

        if (null == savedInstanceState) {
            getExtras();
        } else {
            restoreSavedState(savedInstanceState);
        }
        addDetailsFragment();
        updateNavigationVisibility();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEPS_LIST, (ArrayList<? extends Parcelable>) mStepsList);
        outState.putInt(CURRENT_STEP, currentStep);
    }

    private void restoreSavedState(@NonNull Bundle inState) {
        mStepsList = inState.getParcelableArrayList(STEPS_LIST);
        currentStep = inState.getInt(CURRENT_STEP);
    }

    private void addDetailsFragment() {
        mDetailsFragment = new StepDetailsFragment();
        mDetailsFragment.setStep(mStepsList.get(currentStep));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_details_container, mDetailsFragment)
                .commit();
    }

    private void getExtras() {
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

    private void updateNavigationVisibility() {
        mPrevious.setEnabled(currentStep != 0);
        mNext.setEnabled(mStepsList.size()-1 != currentStep);
    }

    @OnClick(R.id.btn_prev)
    public void previousStep() {
        currentStep--;
        mDetailsFragment.changeStep(mStepsList.get(currentStep));
    }

    @OnClick(R.id.btn_next)
    public void nextStep() {
        currentStep++;
        mDetailsFragment.changeStep(mStepsList.get(currentStep));
    }
}
