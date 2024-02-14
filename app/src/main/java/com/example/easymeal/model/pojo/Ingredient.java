package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("idIngredient")
    private String ingredientId;
    @SerializedName("strIngredient")
    private String ingredientName;

    public String getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}
