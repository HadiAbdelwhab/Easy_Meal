package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaListResponse {

    @SerializedName("meals")
    private List<Area> areas;

    public List<Area> getAreas() {
        return areas;
    }

    public static class Area {

        @SerializedName("strArea")
        private String area;

        public String getArea() {
            return area;
        }
    }
}