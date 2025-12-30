package org.example.springdemo01.service.impl;

import org.example.springdemo01.entity.Comment;
import org.example.springdemo01.entity.Post;
import org.example.springdemo01.entity.User;
import org.example.springdemo01.service.PostService;
import org.example.springdemo01.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DataService implements UserService, PostService {
    
    private List<User> users;
    private List<Post> posts;
    private List<Comment> comments;
    private AtomicInteger userIdSequence;
    private AtomicInteger postIdSequence;
    private AtomicInteger commentIdSequence;

    @PostConstruct
    public void initialize() {
        users = new ArrayList<>();
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        userIdSequence = new AtomicInteger(1);
        postIdSequence = new AtomicInteger(1);
        commentIdSequence = new AtomicInteger(1);
        
        // Add a default user for testing
        users.add(new User("admin", "admin"));
    }

    // UserService implementation
    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean validateUser(String username, String password) {
        User user = findUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public boolean registerUser(String username, String password) {
        if (findUserByUsername(username) != null) {
            return false; // User already exists
        }
        User newUser = new User(username, password);
        users.add(newUser);
        return true;
    }

    @Override
    public User createUser(String username, String password) {
        User user = new User(username, password);
        users.add(user);
        return user;
    }

    // PostService implementation
    @Override
    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public Post findPostById(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Comment> getCommentsForPost(int postId) {
        return comments.stream()
                .filter(c -> c.getPostId() == postId)
                .collect(Collectors.toList());
    }

    @Override
    public void addPost(Post post) {
        posts.add(post);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public void loadCommentsToPosts() {
        // Clear existing comments and rebuild
        for (Post post : posts) {
            post.getComments().clear();
        }
        
        // Add top-level comments to their respective posts
        for (Comment comment : comments) {
            // Only add top-level comments (those without parentId)
            if (!comment.isReply()) {
                for (Post post : posts) {
                    if (post.getId() == comment.getPostId()) {
                        post.addComment(comment);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getNextPostId() {
        return postIdSequence.getAndIncrement();
    }

    @Override
    public int getNextCommentId() {
        return commentIdSequence.getAndIncrement();
    }
    
    // Get replies for a specific comment - not part of interface
    public List<Comment> getRepliesForComment(int commentId) {
        return comments.stream()
                .filter(c -> c.getParentId() != null && c.getParentId() == commentId)
                .collect(Collectors.toList());
    }
}