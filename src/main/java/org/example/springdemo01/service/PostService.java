package org.example.springdemo01.service;

import org.example.springdemo01.entity.Post;
import org.example.springdemo01.entity.Comment;

import java.util.List;

public interface PostService {
    List<Post> getPosts();
    List<Comment> getComments();
    Post findPostById(int id);
    List<Comment> getCommentsForPost(int postId);
    void addPost(Post post);
    void addComment(Comment comment);
    void loadCommentsToPosts();
    int getNextPostId();
    int getNextCommentId();
}