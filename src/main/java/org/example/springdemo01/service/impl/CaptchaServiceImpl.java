package org.example.springdemo01.service.impl;

import org.example.springdemo01.service.CaptchaService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 4;

    @Override
    public BufferedImage generateCaptcha(HttpSession session) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Set background color
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Generate random captcha code
        StringBuilder captchaCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            captchaCode.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }

        // Store the captcha code in session for validation
        session.setAttribute("captcha", captchaCode.toString());

        // Draw captcha code
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        
        // Add some noise
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // Draw captcha characters
        g.setColor(Color.BLACK);
        for (int i = 0; i < captchaCode.length(); i++) {
            int x = i * (WIDTH / CODE_LENGTH) + 10;
            int y = HEIGHT / 2 + 10;
            // Add some rotation to characters
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate((random.nextDouble() - 0.5) * 0.3, x, y);
            g2d.drawString(String.valueOf(captchaCode.charAt(i)), x, y);
            g2d.dispose();
        }

        g.dispose();
        
        return image;
    }

    @Override
    public boolean validateCaptcha(String userInput, HttpSession session) {
        String captcha = (String) session.getAttribute("captcha");
        session.removeAttribute("captcha"); // Remove captcha after validation
        return captcha != null && captcha.equalsIgnoreCase(userInput);
    }

    @Override
    public String storeCaptchaInSession(HttpSession session) {
        StringBuilder captchaCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            captchaCode.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        
        session.setAttribute("captcha", captchaCode.toString());
        return captchaCode.toString();
    }
    
    // 重载方法，返回生成的验证码字符串
    public String generateCaptchaAndReturnCode(HttpSession session) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Set background color
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Generate random captcha code
        StringBuilder captchaCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            captchaCode.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }

        // Store the captcha code in session for validation
        session.setAttribute("captcha", captchaCode.toString());

        // Draw captcha code
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        
        // Add some noise
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // Draw captcha characters
        g.setColor(Color.BLACK);
        for (int i = 0; i < captchaCode.length(); i++) {
            int x = i * (WIDTH / CODE_LENGTH) + 10;
            int y = HEIGHT / 2 + 10;
            // Add some rotation to characters
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate((random.nextDouble() - 0.5) * 0.3, x, y);
            g2d.drawString(String.valueOf(captchaCode.charAt(i)), x, y);
            g2d.dispose();
        }

        g.dispose();
        
        return captchaCode.toString();
    }
}