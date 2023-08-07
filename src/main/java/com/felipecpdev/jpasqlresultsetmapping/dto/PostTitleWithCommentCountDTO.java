package com.felipecpdev.jpasqlresultsetmapping.dto;

public class PostTitleWithCommentCountDTO {
    private final String postTitle;

    private final int commentCount;

    public PostTitleWithCommentCountDTO(
            String postTitle,
            int commentCount) {
        this.postTitle = postTitle;
        this.commentCount = commentCount;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
