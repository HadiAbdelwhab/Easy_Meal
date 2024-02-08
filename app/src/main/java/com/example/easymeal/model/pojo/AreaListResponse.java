package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaListResponse {

    @SerializedName("meals")
    private List<Area> areas;

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public static class Area {

        @SerializedName("strArea")
        private String area;

        private int imageResourceId;

        public Area(String area, int imageResourceId) {
            this.area = area;
            this.imageResourceId = imageResourceId;
        }

        public void setImageResourceId(int imageResourceId) {
            this.imageResourceId = imageResourceId;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }

        public String getArea() {
            return area;
        }
    }
}