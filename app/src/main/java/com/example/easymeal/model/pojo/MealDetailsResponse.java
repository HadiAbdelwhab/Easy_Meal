package com.example.easymeal.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealDetailsResponse {

    @SerializedName("meals")
    private List<MealDetails> meals;

    public List<MealDetails> getMeals() {
        return meals;
    }
    @Entity(tableName = "meals")
    public static class MealDetails {
        @PrimaryKey
        @NonNull
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
        @SerializedName("strIngredient3")
        private String ingredient3;
        @SerializedName("strIngredient4")
        private String ingredient4;
        @SerializedName("strIngredient5")
        private String ingredient5;


        @SerializedName("strSource")
        private String strSource;

        @SerializedName("strImageSource")
        private String strImageSource;


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


        public void setIdMeal(String idMeal) {
            this.idMeal = idMeal;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }

        public void setStrDrinkAlternate(String strDrinkAlternate) {
            this.strDrinkAlternate = strDrinkAlternate;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public void setMealThumb(String mealThumb) {
            this.mealThumb = mealThumb;
        }

        public void setStrTags(String strTags) {
            this.strTags = strTags;
        }

        public void setYoutubeURL(String youtubeURL) {
            this.youtubeURL = youtubeURL;
        }

        public void setIngredient1(String ingredient1) {
            this.ingredient1 = ingredient1;
        }

        public void setIngredient2(String ingredient2) {
            this.ingredient2 = ingredient2;
        }

        public void setStrSource(String strSource) {
            this.strSource = strSource;
        }

        public void setStrImageSource(String strImageSource) {
            this.strImageSource = strImageSource;
        }

        public String getIngredient3() {
            return ingredient3;
        }

        public void setIngredient3(String ingredient3) {
            this.ingredient3 = ingredient3;
        }

        public String getIngredient4() {
            return ingredient4;
        }

        public void setIngredient4(String ingredient4) {
            this.ingredient4 = ingredient4;
        }

        public String getIngredient5() {
            return ingredient5;
        }

        public void setIngredient5(String ingredient5) {
            this.ingredient5 = ingredient5;
        }

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
                    ", strSource='" + strSource + '\'' +
                    ", strImageSource='" + strImageSource + '\'' +
                    '}';
        }
    }
}
