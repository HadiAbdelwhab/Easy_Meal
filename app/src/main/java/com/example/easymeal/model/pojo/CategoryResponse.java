package com.example.easymeal.model.pojo;

import java.util.List;

public class CategoryResponse {
    private List<Category> categories;

    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
