package org.example.springdemo01.controller;

import org.example.springdemo01.service.CaptchaService;
import org.example.springdemo01.service.UserService;
import org.example.springdemo01.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CaptchaService captchaService;
    
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session, 
                       Model model) {
        // Validate user credentials
        boolean isValid = userService.validateUser(username, password);

        if (isValid) {
            User user = userService.findUserByUsername(username);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            return "redirect:/posts";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String password,
                          @RequestParam String confirmPassword, 
                          @RequestParam String captcha,
                          HttpSession session, 
                          Model model) {
        // Validate captcha
        if (!captchaService.validateCaptcha(captcha, session)) {
            model.addAttribute("error", "Invalid captcha");
            return "register";
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        // Check if username already exists
        if (userService.findUserByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // Create new user
        User newUser = userService.createUser(username, password);
        
        // Log the user in automatically
        session.setAttribute("user", newUser);
        session.setAttribute("username", username);
        
        return "redirect:/posts";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}