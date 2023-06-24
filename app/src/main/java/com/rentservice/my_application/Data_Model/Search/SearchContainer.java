package com.rentservice.my_application.Data_Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("posts")
    @Expose
    private List<Post> posts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
