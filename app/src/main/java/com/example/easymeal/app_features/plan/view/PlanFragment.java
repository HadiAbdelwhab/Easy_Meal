package com.example.easymeal.app_features.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easymeal.R;
import com.example.easymeal.app_features.plan.presenter.PlanPresenter;
import com.example.easymeal.app_features.plan.presenter.PlanPresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;

import java.util.List;


public class PlanFragment extends Fragment implements PlanView {


    private static final String TAG = "PlanFragment";
    private PlanPresenter presenter;

    public PlanFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter=new PlanPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));
        presenter.getPlanMeals();
    }

    @Override
    public void showPlanMeals(List<MealDetailsResponse.MealDetails> mealDetails) {
        Log.i(TAG, "showPlanMeals: "+mealDetails);
    }

    @Override
    public void showPlanMealsErrorMessage(String errorMessage) {

    }
}