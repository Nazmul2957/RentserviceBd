package com.example.my_application.Data_Model.Mypost;

import com.example.my_application.Data_Model.SinglePost.Post;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MypostListContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("posts")
    @Expose
    private List<MypostL> posts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MypostL> getPosts() {
        return posts;
    }

    public void setPosts(List<MypostL> posts) {
        this.posts = posts;
    }
}
