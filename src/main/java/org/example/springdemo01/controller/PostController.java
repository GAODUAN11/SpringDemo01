package org.example.springdemo01.controller;

import org.example.springdemo01.service.PostService;
import org.example.springdemo01.entity.Comment;
import org.example.springdemo01.entity.Post;
import org.example.springdemo01.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping({"/", "/posts"})
    public String listPosts(HttpSession session, Model model) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Refresh comments for posts
        postService.loadCommentsToPosts();
        List<Post> posts = postService.getPosts();
        
        model.addAttribute("posts", posts);
        model.addAttribute("username", user.getUsername());
        return "posts";
    }
    
    @GetMapping("/posts/new")
    public String showNewPostForm(HttpSession session, Model model) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("username", user.getUsername());
        return "new-post";
    }
    
    @PostMapping("/posts")
    public String createPost(@RequestParam String title, 
                            @RequestParam String content, 
                            HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty()) {
            int postId = postService.getNextPostId();
            
            Post post = new Post(postId, title, content, user.getUsername());
            postService.addPost(post);
        }
        
        return "redirect:/posts";
    }
    
    @GetMapping("/posts/view/{id}")
    public String viewPost(@PathVariable("id") int postId, 
                          HttpSession session, 
                          Model model) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Refresh comments for posts
        postService.loadCommentsToPosts();
        Post post = postService.findPostById(postId);
        
        if (post != null) {
            List<Comment> postComments = postService.getCommentsForPost(postId);
            
            model.addAttribute("post", post);
            model.addAttribute("comments", postComments);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("commentCount", post.getComments().size());
            return "post-detail";
        } else {
            return "redirect:/posts";
        }
    }
    
    @PostMapping("/posts/comment/{postId}")
    public String addComment(@PathVariable("postId") int postId, 
                            @RequestParam String content, 
                            HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (content != null && !content.trim().isEmpty()) {
            int commentId = postService.getNextCommentId();
            
            Comment comment = new Comment(commentId, content, user.getUsername(), postId);
            postService.addComment(comment);
        }
        
        return "redirect:/posts/view/" + postId;
    }
    
    @PostMapping("/posts/comment/{postId}/reply/{commentId}")
    public String addReply(@PathVariable("postId") int postId,
                          @PathVariable("commentId") int commentId,
                          @RequestParam String content,
                          HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (content != null && !content.trim().isEmpty()) {
            int replyId = postService.getNextCommentId();
            
            Comment reply = new Comment(replyId, content, user.getUsername(), postId, commentId);
            postService.addComment(reply);
        }
        
        return "redirect:/posts/view/" + postId;
    }
}