package com.felipecpdev.jpasqlresultsetmapping.dto;

import com.felipecpdev.jpasqlresultsetmapping.entity.Post;

import java.time.LocalDateTime;

public class PostWithCommentCountDTO {

    private Long id;
    private  String title;
    private LocalDateTime createdOn;

    private Long commentCount;

    public PostWithCommentCountDTO() {
    }

    public PostWithCommentCountDTO(Post post, Long commentCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.createdOn = post.getCreatedOn();
        this.commentCount = commentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
