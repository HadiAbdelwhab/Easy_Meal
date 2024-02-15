package com.example.easymeal.app_features.search.presenter;

import com.example.easymeal.app_features.search.view.SearchView;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.network.meals.NetworkCallBack;

public class SearchPresenterImpl implements SearchPresenter, NetworkCallBack.CategoriesCallBack {
    private SearchView view;
    private MealsRepository repository;

    public SearchPresenterImpl(SearchView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getCategories() {
        repository.getAllCategories(this);
    }

    @Override
    public void onSuccessResult(CategoryResponse categories) {
        view.showCategories(categories);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showCategoriesErrorMessage(errorMessage);
    }
}
