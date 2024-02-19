package com.example.easymeal.network;

public interface MealsRemoteDataSource {

    void getCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack);

    void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack,
                        String mealId);

    void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack);

    void getRandomMeal(NetworkCallBack.RandomMealCallBack randomMealCallBack);

    void getMealsByCategory(NetworkCallBack.MealsByCategoryCallBack mealsByCategoryCallBack,
                            String categoryName);

    void getMealsByArea(NetworkCallBack.MealsByAreaCallBack mealsByAreaCallBack,
                        String areaName);

    void getIngredients(NetworkCallBack.IngredientsCallBack ingredientsCallBack);

    void searchMealByName(NetworkCallBack.SearchMealCallBack searchMealCallBack
            , String mealName);

    void getMealsByIngredient(NetworkCallBack.GetMealsByIngredient getMealsByIngredient,
                              String ingredientName);

}
