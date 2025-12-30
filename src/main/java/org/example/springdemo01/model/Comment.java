package org.example.springdemo01.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {
    private int id;
    private String content;
    private String author;
    private LocalDateTime createdTime;
    private int postId;
    private Integer parentId; // For replies to comments (threaded comments)

    public Comment() {
        this.createdTime = LocalDateTime.now();
    }

    public Comment(int id, String content, String author, int postId) {
        this();
        this.id = id;
        this.content = content;
        this.author = author;
        this.postId = postId;
    }
    
    public Comment(int id, String content, String author, int postId, Integer parentId) {
        this(id, content, author, postId);
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public String getCreatedTimeFormatted() {
        return createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public boolean isReply() {
        return parentId != null;
    }
}