package com.example.easymeal.home.presenter;

import com.example.easymeal.home.view.HomeView;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.network.NetworkCallBack;



public class HomePresenterImpl implements HomePresenter, NetworkCallBack.CategoriesCallBack, NetworkCallBack.AreasCallBack {

    private HomeView view;
    private MealsRepository repository;

    public HomePresenterImpl(HomeView view, MealsRepository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void getAllCategories() {
        repository.getAllCategories(this);
    }

    @Override
    public void getAllAreas() {
        repository.getAllAreas(this);
    }


    @Override
    public void onSuccessResult(CategoryResponse categoryResponse) {
        view.showCategories(categoryResponse);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showCategoriesErrorMessage(errorMessage);
    }


    @Override
    public void onSuccessAreaCallBack(AreaListResponse areaListResponse) {
        view.showAreas(areaListResponse);
    }

    @Override
    public void onFailAreaCallBack(String errorMessage) {
        view.showAreasErrorMessage(errorMessage);
    }
}
