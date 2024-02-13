package com.example.easymeal.app_features.meals.view;

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
import com.example.easymeal.model.pojo.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Context context;
    private List<Meal> mealList;

    public MealsAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(mealList.get(position).getMealImage())
                .apply(new RequestOptions().override(80, 100))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImageView);
        holder.mealNameTextView.setText(mealList.get(position).getMealName());
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mealImageView;
        private TextView mealNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView=itemView.findViewById(R.id.meal_image_view_meals);
            mealNameTextView=itemView.findViewById(R.id.meal_text_view_meals);
        }
    }
}
