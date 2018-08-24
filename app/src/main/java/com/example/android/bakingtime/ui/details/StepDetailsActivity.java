package com.example.android.bakingtime.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    private Step mStep;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.previous_step:
                    // TODO
                    return true;
                case R.id.next_step:
                    // TODO
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (null == savedInstanceState) {
            Intent intent = getIntent();
            if (intent.hasExtra(Step.SAVED_INTENT)) {
                Step step = intent.getParcelableExtra(Step.SAVED_INTENT);
                mStep = (null != step ? step : new Step());
            }
        }

        StepDetailsFragment detailsFragment = new StepDetailsFragment();
        detailsFragment.setStep(mStep);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_details_container, detailsFragment)
                .commit();
    }

}
