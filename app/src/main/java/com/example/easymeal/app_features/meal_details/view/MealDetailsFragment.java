package com.example.easymeal.app_features.meal_details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenter;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenterImpl;
import com.example.easymeal.R;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;


public class MealDetailsFragment extends Fragment implements MealDetailsView{

    private MealDetailsPresenter presenter;
    private static final String TAG = "MealDetailsFragment";

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
        presenter = new MealDetailsPresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity())));
        presenter.getMealDetailsById("52849");

    }

    @Override
    public void showMealDetails(MealDetailsResponse mealDetailsResponse) {
        MealDetailsResponse.MealDetails mealDetails=mealDetailsResponse.getMeals().get(0);
        Log.i(TAG, "showMealDetails: "+mealDetails.toString());

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }
}