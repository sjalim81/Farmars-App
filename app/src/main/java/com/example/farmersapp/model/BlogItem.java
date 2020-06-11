package com.example.farmersapp.model;

import java.util.List;

public class BlogItem {

    private String date,post,like,comment,numberOfPhotos,ownerId,photoUploadStatus,view,blogId,ownerName;
    private List<String>peopleWhoLiked;

    public BlogItem(String date, String post, String like, String comment, String numberOfPhotos, String ownerId, String photoUploadStatus, String view, String blogId, String ownerName, List<String> peopleWhoLiked) {
        this.date = date;
        this.post = post;
        this.like = like;
        this.comment = comment;
        this.numberOfPhotos = numberOfPhotos;
        this.ownerId = ownerId;
        this.photoUploadStatus = photoUploadStatus;
        this.view = view;
        this.blogId = blogId;
        this.ownerName = ownerName;
        this.peopleWhoLiked = peopleWhoLiked;
    }

    public BlogItem() {
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

    public String getLike() {
        return like;
    }

    public String getComment() {
        return comment;
    }

    public String getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getPhotoUploadStatus() {
        return photoUploadStatus;
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
