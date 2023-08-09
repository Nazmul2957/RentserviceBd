
package com.rentservice.rentserVice.Data_Model.Dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("image1")
    @Expose
    private Object image1;
    @SerializedName("image2")
    @Expose
    private Object image2;
    @SerializedName("image3")
    @Expose
    private Object image3;
    @SerializedName("image4")
    @Expose
    private Object image4;
    @SerializedName("image5")
    @Expose
    private Object image5;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("policeStationId")
    @Expose
    private Integer policeStationId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getImage1() {
        return image1;
    }

    public void setImage1(Object image1) {
        this.image1 = image1;
    }

    public Object getImage2() {
        return image2;
    }

    public void setImage2(Object image2) {
        this.image2 = image2;
    }

    public Object getImage3() {
        return image3;
    }

    public void setImage3(Object image3) {
        this.image3 = image3;
    }

    public Object getImage4() {
        return image4;
    }

    public void setImage4(Object image4) {
        this.image4 = image4;
    }

    public Object getImage5() {
        return image5;
    }

    public void setImage5(Object image5) {
        this.image5 = image5;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getPoliceStationId() {
        return policeStationId;
    }

    public void setPoliceStationId(Integer policeStationId) {
        this.policeStationId = policeStationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
