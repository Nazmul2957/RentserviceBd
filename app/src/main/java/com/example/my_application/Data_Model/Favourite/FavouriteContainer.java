package com.example.my_application.Data_Model.Favourite;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FavouriteContainer{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("favourites")
    @Expose
    private List<Favourite> favourites;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

}
