package com.rentservice.my_application.Data_Model.EditPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditPostContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("post")
    @Expose
    private PostEdit post;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PostEdit getPost() {
        return post;
    }

    public void setPost(PostEdit post) {
        this.post = post;
    }

}
