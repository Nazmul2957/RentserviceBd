package com.rentservice.rentserVice.Data_Model.PoliceStation;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoliceStationContainer{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Police> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Police> getData() {
        return data;
    }

    public void setData(List<Police> data) {
        this.data = data;
    }

}

