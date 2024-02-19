package com.example.easymeal.app_features.meals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easymeal.R;
import com.example.easymeal.app_features.meals.presenter.MealsPresenter;
import com.example.easymeal.app_features.meals.presenter.MealsPresenterImpl;
import com.example.easymeal.app_features.meals.view.adpters.MealsAdapter;
import com.example.easymeal.app_features.meals.view.adpters.OnChosenMealListener;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.Meal;
import com.example.easymeal.model.pojo.MealsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;

import java.util.List;


public class MealsFragment extends Fragment implements MealsView, OnChosenMealListener {


    private static final String TAG = "MealsFragment";
    private MealsPresenter presenter;
    private MealsAdapter adapter;
    private RecyclerView mealRecyclerView;
    private List<Meal> meals;
    private String categoryName;
    private String countryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealRecyclerView = view.findViewById(R.id.meals_recycler_view);
        categoryName = MealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        countryName = MealsFragmentArgs.fromBundle(getArguments()).getCountryName();
        Log.i(TAG, "onViewCreated: " + categoryName);
        presenter = new MealsPresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                        MealsLocalDataSourceImpl.getInstance(getActivity())));

        presenter.getMealsByArea(countryName);
        presenter.getMealsByCategory(categoryName);

        //presenter.getMealsByArea("Egyptian");
        // presenter.getMealsByCategory("Pasta");
    }

    public void setAdapter() {
        mealRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        mealRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void showMealsByCategory(MealsResponse mealsResponse) {
        if (mealsResponse != null && mealsResponse.getMeals() != null) {
            meals = mealsResponse.getMeals();
            Log.i(TAG, "showMealsByCategory: " + meals.toString());

            setAdapter();
            adapter = new MealsAdapter(getActivity(), meals, this);
            mealRecyclerView.setAdapter(adapter);
        } else {
            Log.e(TAG, "showMealsByCategory: MealsResponse or meals list is null");
        }
    }

    @Override
    public void showMealsByCategoryErrorMessage(String errorMessage) {

    }

    @Override
    public void showMealsByArea(MealsResponse mealsResponse) {
        if (mealsResponse != null && mealsResponse.getMeals() != null) {
            meals = mealsResponse.getMeals();
            Log.i(TAG, "showMealsByArea: " + meals.toString());

            setAdapter();
            adapter = new MealsAdapter(getActivity(), meals, this);
            mealRecyclerView.setAdapter(adapter);
        } else {
            Log.e(TAG, "showMealsByArea: MealsResponse or meals list is null");
        }

    }

    @Override
    public void showMealsByAreaErrorMessage(String errorMessage) {

    }


    @Override
    public void OnClick(String mealId, View view) {
        MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment toMealDetailsFragment =
                MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(view).navigate(toMealDetailsFragment);

    }
}