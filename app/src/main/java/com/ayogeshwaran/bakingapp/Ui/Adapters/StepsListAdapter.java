package com.ayogeshwaran.bakingapp.Ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.Data.Model.Recipe;
import com.ayogeshwaran.bakingapp.Data.Model.Step;
import com.ayogeshwaran.bakingapp.Interfaces.IOnItemClickedListener;
import com.ayogeshwaran.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListAdapter extends
        RecyclerView.Adapter<StepsListAdapter.StepsListAdapterViewHolder> {

    private final String imageURL = "https://images4.alphacoders.com/878/thumb-350-878402.jpg";

    private Context mContext;

    private List<Step> mSteps;

    private IOnItemClickedListener mOnItemClickedListener;

    public StepsListAdapter(Context context, IOnItemClickedListener onItemClickedListener) {
        mContext = context;
        mOnItemClickedListener = (IOnItemClickedListener) onItemClickedListener;
    }

    public void updateSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    @Override
    public StepsListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.step_list_item, parent,
                false);

        return new StepsListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListAdapterViewHolder holder, int position) {
        String videoThumbnailPath = "";

        if (mSteps.get(position).getVideoURL() != null &&
                !mSteps.get(position).getVideoURL().isEmpty()) {
            videoThumbnailPath = mSteps.get(position).getVideoURL();
        } else {
            videoThumbnailPath = imageURL;
        }

        Picasso.with(mContext).load(videoThumbnailPath)
                .placeholder(mContext.getDrawable(R.drawable.placeholder))
                .error(mContext.getDrawable(R.drawable.placeholder))
                .into(holder.videoThumbailImageView);

        holder.stepDescTextView.setText(String.valueOf(position) + ". ");
        holder.stepDescTextView.append(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps != null) {
            return mSteps.size();
        } else {
            return 0;
        }
    }

    public class StepsListAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.video_thumbail_imageView)
        public ImageView videoThumbailImageView;

        @BindView(R.id.step_desc_text_view)
        public TextView stepDescTextView;

        public StepsListAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            mOnItemClickedListener.OnItemClicked(adapterPosition);
        }
    }
}
