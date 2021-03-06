package com.ayogeshwaran.bakingapp.Ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Data.Model.Remote.RetrofitApiInterface;
import com.ayogeshwaran.bakingapp.Interfaces.IOnItemClickedListener;
import com.ayogeshwaran.bakingapp.Interfaces.IProgressListener;
import com.ayogeshwaran.bakingapp.R;
import com.ayogeshwaran.bakingapp.Ui.Adapters.RecipeAdapter;
import com.ayogeshwaran.bakingapp.Utils.ApiUtils;
import com.ayogeshwaran.bakingapp.Utils.NetworkUtils;
import com.ayogeshwaran.bakingapp.Utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragment extends Fragment implements IOnItemClickedListener, IProgressListener {

    private static final int DELAY_MILLIS = 10000;

    private OnRecipeClickListener mCallback;

    private IProgressListener mProgressListener;

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recipes_Recycler_View)
    public RecyclerView recipesRecyclerView;

    @BindView(R.id.recipe_loading_indicator)
    public ProgressBar recipeLoadingIndicator;

    @BindView(R.id.recipe_error_textview)
    public TextView recipeErrorTextView;

    private RecipeAdapter recipeAdapter;

    private List<Recipe> mRecipes;

    private final String RV_POSITION = "postion";

    private Bundle mSavedInstanceState;

    public RecipesFragment() {

    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(List<Recipe>recipes , int position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipes, container,
                false);

        ButterKnife.bind(this, rootView);

        initViews();

        if(savedInstanceState != null){
            //to scroll to existing position which exist before rotation.
            mSavedInstanceState = savedInstanceState;
        }

        downloadRecipes();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeClickListener) context;
            mProgressListener = (IProgressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RV_POSITION,
                recipesRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void OnItemClicked(int position) {
//        Sending the callback to MainActivity
        mCallback.onRecipeSelected(mRecipes, position);
    }

    private void initViews() {
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(), 1);

        recipesRecyclerView.setLayoutManager(gridLayoutManager);

        recipesRecyclerView.setHasFixedSize(true);

        if (recipeAdapter == null) {
            recipeAdapter = new RecipeAdapter(getContext(), this);
        }

        recipesRecyclerView.setAdapter(recipeAdapter);
    }

    private void showLoading() {
        recipeLoadingIndicator.setVisibility(View.VISIBLE);
        recipesRecyclerView.setVisibility(View.INVISIBLE);
        recipeErrorTextView.setVisibility(View.INVISIBLE);
    }

    private void showError(String errorMessage) {
        recipeErrorTextView.setVisibility(View.VISIBLE);
        recipeErrorTextView.setText(errorMessage);
        recipeLoadingIndicator.setVisibility(View.INVISIBLE);
        recipesRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showRecipesView() {
        recipesRecyclerView.setVisibility(View.VISIBLE);
        recipeErrorTextView.setVisibility(View.INVISIBLE);
        recipeLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void downloadRecipes() {
        if (NetworkUtils.isOnline(getContext())) {
            RetrofitApiInterface retrofitApiInterface = ApiUtils.getRetrofitClient(AppConstants.RECIPES_URL);

            showLoading();
            mProgressListener.onStarted();

            retrofitApiInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    if (response.isSuccessful()) {
                        showRecipesView();
                        mRecipes = response.body();
                        recipeAdapter.updateRecipes(mRecipes);
                        restoreRecyclerViewState();
                        insertRecipes(mRecipes);

                        mProgressListener.onDone();
                        Log.d("MainActivity", "posts loaded from API");
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Response not okay when getting recipes : " + statusCode);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    showError(getString(R.string.problem_loading_recipes));
                    Log.d(TAG, "error loading from API");

                }
            });
        } else {
            showError(getString(R.string.check_connection));
        }
    }

    private void restoreRecyclerViewState() {
        if (mSavedInstanceState != null) {
            recipesRecyclerView.getLayoutManager().
                    onRestoreInstanceState(mSavedInstanceState.getParcelable(RV_POSITION));
        }
    }

    private void insertRecipes(List<Recipe> recipes) {
        SharedPreferences mPrefs = SharedPreferenceUtils.getSharedPreferences(getActivity());

        if (mPrefs != null) {
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(recipes);
            prefsEditor.putString(AppConstants.RECIPE_WIDGET_INFO_KEY, json);
            prefsEditor.apply();
            prefsEditor.commit();
        }
    }

    private static int getSpanCount(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        float dpWidth = context.getResources().getDisplayMetrics().widthPixels / density;
        return Math.round(dpWidth / 200);
    }

    @Override
    public void onStarted() {
        mProgressListener.onStarted();
    }

    @Override
    public void onDone() {
        mProgressListener.onDone();
    }
}
