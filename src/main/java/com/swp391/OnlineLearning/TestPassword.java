package com.swp391.OnlineLearning;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Result_123456: " + encoder.matches("123456", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07c209qnMrHlClDW2W"));
        System.out.println("Result_12345678: " + encoder.matches("12345678", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07c209qnMrHlClDW2W"));
        System.out.println("Result_password: " + encoder.matches("password", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07c209qnMrHlClDW2W"));
        System.out.println("Result_admin: " + encoder.matches("admin", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07c209qnMrHlClDW2W"));
    }
}
