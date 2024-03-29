package com.example.easymeal.app_features.home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.easymeal.R;
import com.example.easymeal.model.pojo.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private static final String TAG = "";
    private Context context;
    private List<Category> categoryList;
    private OnChosenCategoryClickListener listener;

    public CategoriesAdapter(FragmentActivity activity, List<Category> categoryList, OnChosenCategoryClickListener listener) {
        this.context = activity; // Use FragmentActivity as the context
        this.categoryList = categoryList;
        this.listener = listener;
    }



    public CategoriesAdapter(FragmentActivity activity, List<String> categoriesName) {
        this(activity, null, null);
        // Initialize your categoryList based on categoriesName if needed.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getCategoryThumb())
                .apply(new RequestOptions().override(150, 150))
                .placeholder(R.drawable.laod)
                .error(R.drawable.laod)
                .into(holder.categoryImageView);
        holder.categoryNameTextView.setText(categoryList.get(position).getCategoryName());
        holder.categoryCardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(categoryList.get(position).getCategoryName(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImageView;
        private TextView categoryNameTextView;
        private CardView categoryCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.category_image_view);
            categoryNameTextView = itemView.findViewById(R.id.category_name_text_view);
            categoryCardView = itemView.findViewById(R.id.category_card);
        }
    }
}
