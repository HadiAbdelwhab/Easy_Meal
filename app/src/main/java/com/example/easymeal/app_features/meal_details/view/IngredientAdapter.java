package com.example.easymeal.app_features.meal_details.view;

import static com.example.easymeal.util.Constants.BASE_URL;
import static com.example.easymeal.util.Constants.IMAGES_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easymeal.R;



import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<String> ingredients;
    private Context context;


    public IngredientAdapter(List<String> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredientTextView.setText(ingredients.get(position));
        Glide.with(context).load(IMAGES_BASE_URL+ingredients.get(position)+"-Small.png")
                .placeholder(R.drawable.laod)
                .error(R.drawable.laod)
                .into(holder.ingredientImageView);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ingredientImageView;
        private TextView ingredientTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImageView = itemView.findViewById(R.id.ingredient_image_view);
            ingredientTextView = itemView.findViewById(R.id.ingredient_text_view);

        }
    }
}
