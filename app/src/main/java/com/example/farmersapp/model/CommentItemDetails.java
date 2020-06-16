package com.example.farmersapp.model;

public class CommentItemDetails {

    String userId;
    String comment;
    String name;
    String blogId;

    public CommentItemDetails(String userId, String comment, String name, String blogId) {
        this.userId = userId;
        this.comment = comment;
        this.name = name;
        this.blogId = blogId;
    }

    public CommentItemDetails() {
    }

    public String getBlogId() {
        return blogId;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }
}
