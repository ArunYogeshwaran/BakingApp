package com.ayogeshwaran.bakingapp.Ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.AppConstants;
import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Interfaces.IOnItemClickedListener;
import com.ayogeshwaran.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private List<Recipe> mRecipes;

    private Context mContext;

    private IOnItemClickedListener mOnItemClickedListener;

    public RecipeAdapter(Context context, IOnItemClickedListener onItemClickedListener) {
        mContext = context;
        mOnItemClickedListener = onItemClickedListener;
    }

    public void updateRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        } else {
            return 0;
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent,
                false);

        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        if (TextUtils.isEmpty(mRecipes.get(position).getImage())) {
            Picasso.with(mContext).load(AppConstants.imageURL)
                    .placeholder(mContext.getDrawable(R.drawable.placeholder))
                    .error(mContext.getDrawable(R.drawable.placeholder))
                    .into(holder.recipeImageView);
        } else {
            Picasso.with(mContext).load(mRecipes.get(position).getImage())
                    .placeholder(mContext.getDrawable(R.drawable.placeholder))
                    .error(mContext.getDrawable(R.drawable.placeholder))
                    .into(holder.recipeImageView);
        }

        holder.recipeNameTextView.setText(mRecipes.get(position).getName());
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.recipe_image_view)
        public ImageView recipeImageView;

        @BindView(R.id.recipe_name_text_view)
        public TextView recipeNameTextView;

        public RecipeAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            mOnItemClickedListener.OnItemClicked(adapterPosition);
        }
    }
}
