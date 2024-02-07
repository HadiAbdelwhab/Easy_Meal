package com.example.easymeal.network;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface NetworkCallBack {
    interface CategoriesCallBack{
        public void onSuccessResult(CategoryResponse categories);
        public void onFailure(String errorMessage);
    }
    interface MealDetailsCallBack{
        public void onSuccessMealDetails(MealDetailsResponse mealDetailsResponse);
        public void onFailMealDetails(String errorMessage);

    }
    interface AreasCallBack{
        public void onSuccessAreaCallBack(AreaListResponse areaListResponse);
        public void onFailAreaCallBack(String errorMessage);
    }
}
