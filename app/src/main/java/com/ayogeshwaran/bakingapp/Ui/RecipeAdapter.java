package com.ayogeshwaran.bakingapp.Ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private final String tempURL = "https://images4.alphacoders.com/878/thumb-350-878402.jpg";

    private List<Recipe> mRecipes;

    private Context mContext;

    public RecipeAdapter(Context context) {
        mContext = context;
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
        String recipeThumbNailPath = "";

        if (mRecipes != null) {
            recipeThumbNailPath = mRecipes.get(position).getSteps().get(position).getVideoURL();
        }

        Picasso.with(mContext).load(tempURL)
                .placeholder(mContext.getDrawable(R.drawable.placeholder))
                .error(mContext.getDrawable(R.drawable.error_placeholder))
                .into(holder.recipeImageView);

        holder.recipeNameTextView.setText(mRecipes.get(position).getName());

//        if (!recipeThumbNailPath.isEmpty()) {
//            try {
//                Picasso.with(mContext).load(tempURL)
//                        .into(holder.recipeImageView);
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        }
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

        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1,
                    MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
