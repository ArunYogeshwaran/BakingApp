package com.ayogeshwaran.bakingapp.Ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.Interfaces.IOnItemClickedListener;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.Adapters.StepsListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment implements IOnItemClickedListener {

    private OnStepClickListener mCallback;

    @BindView(R.id.steps_recycler_view)
    public RecyclerView stepsRecyclerView;

    private Recipe mRecipe;

    private List<Step> mSteps;

    private final String STEPS_RV_POSITION = "steps_postion";

    private Bundle mSavedInstanceState;

    @Override
    public void OnItemClicked(int position) {
        Step step = mSteps.get(position);

        mCallback.onStepSelected(step);
    }


    public interface OnStepClickListener {
        void onStepSelected(Step step);
    }

    public StepsFragment() {

    }

    public void setRecipe(Recipe recipe) {
        if (recipe != null) {
            mRecipe = recipe;
            mSteps = recipe.getSteps();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps, container,
                false);

        setRetainInstance(true);

        ButterKnife.bind(this, rootView);

        initViews();

        if(savedInstanceState != null){
            // scroll to existing position which exist before rotation.
            mSavedInstanceState = savedInstanceState;
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEPS_RV_POSITION,
                stepsRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    private void initViews() {
        buildStepsList();
    }

    private void buildStepsList() {
        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), 1, false);

        stepsRecyclerView.setLayoutManager(linearLayoutManager);

        stepsRecyclerView.setHasFixedSize(false);

        StepsListAdapter stepsListAdapter = new StepsListAdapter(getContext(), this);

        stepsRecyclerView.setAdapter(stepsListAdapter);

        stepsListAdapter.updateSteps(mRecipe);
        if (mSavedInstanceState != null) {
            stepsRecyclerView.getLayoutManager().onRestoreInstanceState(
                    mSavedInstanceState.getParcelable(STEPS_RV_POSITION));
        }
    }
}
