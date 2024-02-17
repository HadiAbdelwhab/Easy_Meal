package com.example.easymeal.app_features.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easymeal.R;
import com.example.easymeal.app_features.favourite.presenter.FavouritePresenter;
import com.example.easymeal.app_features.favourite.presenter.FavouritePresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteView,OnDeleteMealListener{

    private RecyclerView favouriteMealRecyclerView;

    private FavouritePresenter presenter;
    private FavouriteMealsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        presenter=new FavouritePresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));
        presenter.getFavouriteMeals();

    }
    private void initView(View view){
        favouriteMealRecyclerView=view.findViewById(R.id.favourite_meals_recycler_view);
    }
    private void setFavouriteMealRecyclerView(){
        favouriteMealRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        favouriteMealRecyclerView.setLayoutManager(manager);
    }


    @Override
    public void showFavouriteMeals(List<MealDetailsResponse.MealDetails> mealDetails) {
        setFavouriteMealRecyclerView();
        adapter=new FavouriteMealsAdapter(mealDetails, getActivity(),this);
        favouriteMealRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showFavouriteMealsErrorMessage(String errorMessage) {

    }

    @Override
    public void onClick(MealDetailsResponse.MealDetails mealDetails) {
        presenter.deleteFavouriteMeal(mealDetails);
        adapter.notifyDataSetChanged();
    }
}