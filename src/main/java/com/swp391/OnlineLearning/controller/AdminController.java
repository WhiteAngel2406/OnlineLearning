package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.Model.CourseCategory;
import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import com.swp391.OnlineLearning.Model.dto.OrderFilter;
import com.swp391.OnlineLearning.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    //===================== DASHBOARD ========================
    @GetMapping("")
    public String admin(Model model) {
        return "admin/dashboard";
    }


}