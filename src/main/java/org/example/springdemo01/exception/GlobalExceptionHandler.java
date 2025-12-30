package org.example.springdemo01.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, HttpServletRequest request) {
        model.addAttribute("error", "An error occurred: " + e.getMessage());
        // Determine which page to return based on the request URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            return "login";
        } else if (requestURI.contains("/register")) {
            return "register";
        } else if (requestURI.contains("/posts")) {
            return "posts";
        } else {
            return "error";
        }
    }
}