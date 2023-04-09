package com.example.my_application.Data_Model.PoliceStationByDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PolicestationByDistrictContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<PolicestationShowByDistrict> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PolicestationShowByDistrict> getData() {
        return data;
    }

    public void setData(List<PolicestationShowByDistrict> data) {
        this.data = data;
    }
}
