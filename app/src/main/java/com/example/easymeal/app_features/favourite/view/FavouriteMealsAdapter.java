package com.example.easymeal.app_features.favourite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.adapters.CategoriesAdapter;
import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.ViewHolder> {
    private List<MealDetailsResponse.MealDetails> mealDetails;
    private Context context;
    private OnDeleteMealListener listener;

    public FavouriteMealsAdapter(List<MealDetailsResponse.MealDetails> mealDetails, Context context, OnDeleteMealListener listener) {
        this.mealDetails = mealDetails;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favourite_meal_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.meaNameTextView.setText(mealDetails.get(position).getMealName());
        Glide.with(context).load(mealDetails.get(position).getMealThumb())
                .apply(new RequestOptions().override(150, 150))
                .placeholder(R.drawable.laod)
                .error(R.drawable.laod)
                .into(holder.mealImageView);
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(mealDetails.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImageView, deleteImageView;
        private TextView meaNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.saved_meal_image_view);
            deleteImageView = itemView.findViewById(R.id.delete_from_favourite_image_view);
            meaNameTextView = itemView.findViewById(R.id.saved_meal_name);
        }
    }
}
