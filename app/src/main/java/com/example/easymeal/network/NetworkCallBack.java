package com.example.easymeal.network;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

public interface NetworkCallBack {
    interface CategoriesCallBack {
        void onSuccessResult(CategoryResponse categories);

        void onFailure(String errorMessage);
    }

    interface MealDetailsCallBack {
        void onSuccessMealDetails(MealDetailsResponse mealDetailsResponse);

        void onFailMealDetails(String errorMessage);

    }

    interface AreasCallBack {
        void onSuccessAreaCallBack(AreaListResponse areaListResponse);

        void onFailAreaCallBack(String errorMessage);
    }

    interface RandomMealCallBack {
        void onSuccessRandomMeal(MealDetailsResponse mealDetails);

        void onFailRandomMeal(String errorMessage);

    }

    interface MealsByCategoryCallBack {
        void onSuccessMealsByCategory(MealsResponse mealsResponse);

        void onFailMealsByCategory(String errorMessage);
    }

    interface MealsByAreaCallBack {

        void onSuccessMealsByArea(MealsResponse mealsResponse);

        void onFailMealsByArea(String errorMessage);
    }

    interface IngredientsCallBack {
        void onSuccessIngredients(IngredientsResponse ingredientsResponse);

        void onFailIngredients(String errorMessage);

    }

    interface SearchMealCallBack {
        void onSuccessSearchMeal(MealDetailsResponse mealDetailsResponse);

        void onFailSearchMeal(String errorMessage);
    }
}
