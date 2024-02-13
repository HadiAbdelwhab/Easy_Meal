package com.example.easymeal.app_features.home.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.HomeFragmentDirections;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getCategoryThumb())
                .apply(new RequestOptions().override(150, 150))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.categoryImageView);
        holder.categoryNameTextView.setText(categoryList.get(position).getCategoryName());
        holder.categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: "+categoryList.get(position).getCategoryId());
                listener.onClickListener(categoryList.get(position).getCategoryName(), v);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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
