package com.example.farmersapp.model;

import java.sql.Timestamp;
import java.util.List;

public class BlogItem {

    private String date,post,numberOfPhotos,ownerId,view,blogId,ownerName;
    private List<String>peopleWhoLiked;
    int like,comment;
    Timestamp timestamp;

    public BlogItem(String date, String post, String numberOfPhotos, String ownerId, String view, String blogId, String ownerName, List<String> peopleWhoLiked, int like, int comment,Timestamp timestamp) {
        this.date = date;
        this.post = post;
        this.numberOfPhotos = numberOfPhotos;
        this.ownerId = ownerId;
        this.view = view;
        this.blogId = blogId;
        this.ownerName = ownerName;
        this.peopleWhoLiked = peopleWhoLiked;
        this.like = like;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public BlogItem() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public List<String> getPeopleWhoLiked() {
        return peopleWhoLiked;
    }

    public String getDate() {
        return date;
    }

    public String getPost() {
        return post;
    }

    public int getLike() {
        return like;
    }

    public int getComment() {
        return comment;
    }

    public String getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getView() {
        return view;
    }

    public String getBlogId() {
        return blogId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
