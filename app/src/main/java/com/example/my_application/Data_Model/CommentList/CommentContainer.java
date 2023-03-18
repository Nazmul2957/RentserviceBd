package com.example.my_application.Data_Model.CommentList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentContainer {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
