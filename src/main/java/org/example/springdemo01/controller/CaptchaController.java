package org.example.springdemo01.controller;

import org.example.springdemo01.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CaptchaController {
    
    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/captcha")
    public void generateCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        // Generate captcha image and store code in session
        BufferedImage image = captchaService.generateCaptcha(session);

        // Set response content type and write image
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }
}