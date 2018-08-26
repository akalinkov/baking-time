package com.example.android.bakingtime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Step;
import com.example.android.bakingtime.ui.listeners.OnStepsItemClickListener;
import com.example.android.bakingtime.ui.viewholders.StepsViewHolder;

import java.util.List;

import butterknife.BindView;

public class StepsListAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private static final String TAG = StepsListAdapter.class.getSimpleName();

    private List<Step> mStepsList;
    private Context mContext;
    private OnStepsItemClickListener mCallback;
    private int mSelectedPosition = RecyclerView.NO_POSITION;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    public StepsListAdapter(Context context,
                            @NonNull List<Step> stepsList,
                            OnStepsItemClickListener stepClickListener) {
        mContext = context;
        mStepsList = stepsList;
        mCallback = stepClickListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stepView = LayoutInflater.from(mContext).inflate(R.layout.item_step, parent, false);
        return new StepsViewHolder(stepView, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.itemView.setSelected(mSelectedPosition == position);
        holder.bind(mStepsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (null == mStepsList) {
            return 0;
        }
        return mStepsList.size();
    }

    public void setSelectedPosition(int position) {
        Log.d(TAG, "setSelectedPosition: #" + position);
        mSelectedPosition = position;
    }

    public void notifySelectedItemchanged() {
        Log.d(TAG, "notifySelectedItemchanged: #" + mSelectedPosition);
        notifyItemChanged(mSelectedPosition);
    }
}
