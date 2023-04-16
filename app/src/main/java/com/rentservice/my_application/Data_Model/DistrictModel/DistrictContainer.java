package com.rentservice.my_application.Data_Model.DistrictModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DistrictContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<District> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<District> getData() {
        return data;
    }

    public void setData(List<District> data) {
        this.data = data;
    }

}
