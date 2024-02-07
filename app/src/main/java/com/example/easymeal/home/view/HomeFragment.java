package com.example.easymeal.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easymeal.R;
import com.example.easymeal.home.presenter.HomePresenter;
import com.example.easymeal.home.presenter.HomePresenterImpl;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.Category;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;

import java.util.List;


public class HomeFragment extends Fragment implements HomeView {


    private static final String TAG = "HomeFragment";
    private List<Category> categories;
    private HomePresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
presenter=new HomePresenterImpl(this,
        MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity())));
        presenter.getAllCategories();
        presenter.getAllAreas();
        Log.i(TAG, "onViewCreated: "+categories);
    }

    @Override
    public void showCategories(CategoryResponse categoryResponse) {
        categories=categoryResponse.getCategories();
        //Log.i(TAG, "showCategories: "+categories);
    }

    @Override
    public void showCategoriesErrorMessage(String errorMassage) {
        //Log.i(TAG, "showErrorMessage: "+errorMassage);
    }

    @Override
    public void showAreas(AreaListResponse areaListResponse) {
        Log.i(TAG, "showAreas: "+areaListResponse.getAreas());
    }

    @Override
    public void showAreasErrorMessage(String errorMessage) {
        Log.i(TAG, "showAreasErrorMessage: "+errorMessage);
    }
}