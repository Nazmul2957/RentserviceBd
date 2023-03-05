package com.example.my_application.Data_Model.Category;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CategoryContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CategoryList> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CategoryList> getData() {
        return data;
    }

    public void setData(List<CategoryList> data) {
        this.data = data;
    }

}
