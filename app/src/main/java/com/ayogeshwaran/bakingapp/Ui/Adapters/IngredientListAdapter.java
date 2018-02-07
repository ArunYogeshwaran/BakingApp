package com.ayogeshwaran.bakingapp.Ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayogeshwaran.bakingapp.Data.Model.Ingredient;
import com.ayogeshwaran.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListAdapter
        extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;

    private Context mContext;

    public IngredientListAdapter(Context context) {
        mContext = context;
    }

    public void updateIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.ingredient_list_item, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.ingredientName.setText(mIngredients.get(position).getIngredient());
        holder.ingredientQuantity.setText(mIngredients.get(position).getQuantity().toString());
        holder.ingredientQuantity.append(mIngredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        } else {
            return 0;
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name_text_view)
        public TextView ingredientName;

        @BindView(R.id.ingredient_quantity_text_view)
        public TextView ingredientQuantity;

        public IngredientViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            ingredientName.setSelected(true);
        }
    }

}
