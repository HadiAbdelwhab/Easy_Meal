package com.example.easymeal.model.pojo;

public class Meal {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;

    public Meal(String strMeal, String strMealThumb, String idMeal) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    @Override
    public String toString() {
        return String.format("Meal: %s\nMeal Thumbnail: %s\nMeal ID: %s\n", strMeal, strMealThumb, idMeal);
    }
}