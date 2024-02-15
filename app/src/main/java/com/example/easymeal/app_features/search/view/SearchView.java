package com.example.easymeal.app_features.search.view;

import com.example.easymeal.model.pojo.CategoryResponse;

public interface SearchView {
    void showCategories(CategoryResponse categoryResponse);
    void showCategoriesErrorMessage(String ErrorMassage);
}
