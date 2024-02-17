package com.example.easymeal.app_features.favourite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.adapters.CategoriesAdapter;
import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.ViewHolder> {
    private List<MealDetailsResponse.MealDetails> mealDetails;
    private Context context;
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mealImageView, deleteImageView;
        private TextView meaNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView=itemView.findViewById(R.id.saved_meal_image_view);
            deleteImageView=itemView.findViewById(R.id.delete_from_favourite_image_view);
        }
    }
}
