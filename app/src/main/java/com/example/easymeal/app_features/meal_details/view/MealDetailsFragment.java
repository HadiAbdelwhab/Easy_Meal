package com.example.easymeal.app_features.meal_details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenter;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenterImpl;
import com.example.easymeal.R;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;


public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private MealDetailsPresenter presenter;
    private static final String TAG = "MealDetailsFragment";

    private ImageView mealImageView;
    private TextView maelNameTextView, instructionsTextView, areaTextView;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealImageView = view.findViewById(R.id.meal_image_view);
        maelNameTextView = view.findViewById(R.id.meal_name_text_view);
        instructionsTextView = view.findViewById(R.id.instruction_text_view);
        areaTextView = view.findViewById(R.id.area_text_view);

        String mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();

        presenter = new MealDetailsPresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity())));
        presenter.getMealDetailsById(mealId);

    }

    @Override
    public void showMealDetails(MealDetailsResponse mealDetailsResponse) {
        MealDetailsResponse.MealDetails randomMeal = mealDetailsResponse.getMeals().get(0);
        Glide.with(getActivity())
                .load(randomMeal.getMealThumb())
                .error(R.drawable.laod)
                .into(mealImageView);
        maelNameTextView.setText(randomMeal.getMealName());
        instructionsTextView.setText(randomMeal.getInstructions());
        //areaTextView.setText(randomMeal.getArea());

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }
}