package com.example.easymeal.app_features.home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easymeal.R;
import com.example.easymeal.model.pojo.AreaListResponse;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    private Context context;
    private List<AreaListResponse.Area> areaList;
    private OnChosenAreaListener listener;

    public CountriesAdapter(Context context, List<AreaListResponse.Area> areaList, OnChosenAreaListener listener) {
        this.context = context;
        this.areaList = areaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.area_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.countryTextView.setText(areaList.get(position).getArea());
        holder.countryImageView.setImageResource(areaList.get(position).getImageResourceId());
        holder.countryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAreaClick(areaList.get(position).getArea(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView countryImageView;
        private TextView countryTextView;
        private CardView countryCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImageView = itemView.findViewById(R.id.area_image_view);
            countryTextView = itemView.findViewById(R.id.area_text_view);
            countryCardView = itemView.findViewById(R.id.area_card_view);
        }
    }
}
