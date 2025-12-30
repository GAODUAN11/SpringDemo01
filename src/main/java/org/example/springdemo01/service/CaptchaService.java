package org.example.springdemo01.service;

import jakarta.servlet.http.HttpSession;

import java.awt.image.BufferedImage;

public interface CaptchaService {
    BufferedImage generateCaptcha(HttpSession session);
    boolean validateCaptcha(String userInput, HttpSession session);
    String storeCaptchaInSession(HttpSession session);
}