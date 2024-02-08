package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("idCategory")
    private String categoryId;
    @SerializedName("strCategory")
    private String categoryName;
    @SerializedName("strCategoryThumb")
    private String categoryThumb;
    @SerializedName("strCategoryDescription")
    private String categoryDescription;

    public Category(String categoryId, String categoryName, String categoryThumb, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryThumb = categoryThumb;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryThumb() {
        return categoryThumb;
    }

    public void setCategoryThumb(String categoryThumb) {
        this.categoryThumb = categoryThumb;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public String toString() {
        return String.format("Category ID: %s\nCategory Name: %s\nDescription: %s\n", categoryId, categoryName, categoryDescription);
    }
}
