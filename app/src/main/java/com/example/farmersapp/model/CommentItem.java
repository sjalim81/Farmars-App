package com.example.farmersapp.model;

import java.util.List;

public class CommentItem {

    List<CommentItemDetails> commentList;

    public CommentItem(List<CommentItemDetails> commentList) {
        this.commentList = commentList;
    }

    public List<CommentItemDetails> getCommentList() {
        return commentList;
    }
}
