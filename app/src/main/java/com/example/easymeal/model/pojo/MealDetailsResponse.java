package com.example.easymeal.model.pojo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealDetailsResponse {

    @SerializedName("meals")
    private List<MealDetails> meals;

    public List<MealDetails> getMeals() {
        return meals;
    }

    public static class MealDetails {

        @SerializedName("idMeal")
        private String idMeal;

        @SerializedName("strMeal")
        private String mealName;

        @SerializedName("strDrinkAlternate")
        private String strDrinkAlternate;

        @SerializedName("strCategory")
        private String category;

        @SerializedName("strArea")
        private String area;

        @SerializedName("strInstructions")
        private String instructions;

        @SerializedName("strMealThumb")
        private String mealThumb;

        @SerializedName("strTags")
        private String strTags;

        @SerializedName("strYoutube")
        private String youtubeURL;

        @SerializedName("strIngredient1")
        private String ingredient1;

        @SerializedName("strIngredient2")
        private String ingredient2;

        // Continue defining the rest of the ingredients and measures...

        @SerializedName("strSource")
        private String strSource;

        @SerializedName("strImageSource")
        private String strImageSource;

        // Add getters for other fields as needed

        public String getIdMeal() {
            return idMeal;
        }

        public String getMealName() {
            return mealName;
        }

        public String getStrDrinkAlternate() {
            return strDrinkAlternate;
        }

        public String getCategory() {
            return category;
        }

        public String getArea() {
            return area;
        }

        public String getInstructions() {
            return instructions;
        }

        public String getMealThumb() {
            return mealThumb;
        }

        public String getStrTags() {
            return strTags;
        }

        public String getYoutubeURL() {
            return youtubeURL;
        }

        public String getIngredient1() {
            return ingredient1;
        }

        public String getIngredient2() {
            return ingredient2;
        }

        // Continue adding getters for the rest of the ingredients and measures...

        public String getStrSource() {
            return strSource;
        }

        public String getStrImageSource() {
            return strImageSource;
        }

        @NonNull
        @Override
        public String toString() {
            return "MealDetails{" +
                    "idMeal='" + idMeal + '\'' +
                    ", mealName='" + mealName + '\'' +
                    ", strDrinkAlternate='" + strDrinkAlternate + '\'' +
                    ", category='" + category + '\'' +
                    ", area='" + area + '\'' +
                    ", instructions='" + instructions + '\'' +
                    ", mealThumb='" + mealThumb + '\'' +
                    ", strTags='" + strTags + '\'' +
                    ", youtubeURL='" + youtubeURL + '\'' +
                    ", ingredient1='" + ingredient1 + '\'' +
                    ", ingredient2='" + ingredient2 + '\'' +
                    // Continue adding the rest of the ingredients and measures...
                    ", strSource='" + strSource + '\'' +
                    ", strImageSource='" + strImageSource + '\'' +
                    '}';
        }
    }
}
