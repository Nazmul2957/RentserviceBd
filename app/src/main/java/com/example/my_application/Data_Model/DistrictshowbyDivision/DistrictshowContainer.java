package com.example.my_application.Data_Model.DistrictshowbyDivision;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictshowContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DistrictShow> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DistrictShow> getData() {
        return data;
    }

    public void setData(List<DistrictShow> data) {
        this.data = data;
    }

}
