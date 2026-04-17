package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Model.Slider;
import com.swp391.OnlineLearning.Model.Token;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.BlogDTO;
import com.swp391.OnlineLearning.Model.dto.CourseFeedbackStats;
import com.swp391.OnlineLearning.Model.dto.UserDTO;
import com.swp391.OnlineLearning.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;

    public AuthController(UserService userService, TokenService tokenService, EmailService emailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    // ---------------- HOME ----------------
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    // ---------------- LOGIN ----------------

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    // ---------------- REGISTER ----------------

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "auth/register";
    }

}
