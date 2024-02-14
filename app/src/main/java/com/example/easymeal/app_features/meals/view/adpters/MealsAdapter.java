package com.example.easymeal.app_features.meals.view.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.easymeal.R;
import com.example.easymeal.model.pojo.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Context context;
    private List<Meal> mealList;
    private OnChosenMealListener onChosenMeallistener;

    public MealsAdapter(Context context, List<Meal> mealList, OnChosenMealListener onChosenMeallistener) {
        this.context = context;
        this.mealList = mealList;
        this.onChosenMeallistener=onChosenMeallistener;
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
                .placeholder(R.drawable.laod)
                .error(R.drawable.laod)
                .into(holder.mealImageView);
        holder.mealNameTextView.setText(mealList.get(position).getMealName());
        holder.mealCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChosenMeallistener.OnClick(mealList.get(position).getIdMeal(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImageView;
        private TextView mealNameTextView;
        private CardView mealCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.meal_image_view_meals);
            mealNameTextView = itemView.findViewById(R.id.meal_text_view_meals);
            mealCardView = itemView.findViewById(R.id.meal_card);
        }
    }
}
