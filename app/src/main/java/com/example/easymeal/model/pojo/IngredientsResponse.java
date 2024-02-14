package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse {

    @SerializedName("meals")
    private List<Ingredient> ingredientsList;

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }
}
