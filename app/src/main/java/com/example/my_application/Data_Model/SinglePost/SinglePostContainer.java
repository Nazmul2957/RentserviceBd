package com.example.my_application.Data_Model.SinglePost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SinglePostContainer {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("post")
    @Expose
    private Post post;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
