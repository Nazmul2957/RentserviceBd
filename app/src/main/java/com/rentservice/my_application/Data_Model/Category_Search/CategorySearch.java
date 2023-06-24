package com.rentservice.my_application.Data_Model.Category_Search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategorySearch {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("posts")
    @Expose
    private List<PostCategory> posts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PostCategory> getPosts() {
        return posts;
    }

    public void setPosts(List<PostCategory> posts) {
        this.posts = posts;
    }

}
