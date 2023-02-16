package com.example.my_application.Data_Model.Division;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DivisionContainer{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Division> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Division> getData() {
        return data;
    }

    public void setData(List<Division> data) {
        this.data = data;
    }

}
