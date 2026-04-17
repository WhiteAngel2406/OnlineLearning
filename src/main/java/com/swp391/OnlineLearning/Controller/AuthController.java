package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.UserDTO;
import com.swp391.OnlineLearning.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
