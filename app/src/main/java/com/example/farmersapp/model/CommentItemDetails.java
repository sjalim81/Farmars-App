package com.example.farmersapp.model;

public class CommentItemDetails {

    String userId;
    String comment;
    String name;

    public CommentItemDetails(String userId, String comment, String name) {
        this.userId = userId;
        this.comment = comment;
        this.name = name;
    }

    public CommentItemDetails() {
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
