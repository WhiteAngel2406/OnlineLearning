package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Repository.BlogRepository;
import com.swp391.OnlineLearning.Repository.CourseRepository;
import com.swp391.OnlineLearning.Repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/marketing")
public class MarketingController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BlogRepository blogRepository;

    public MarketingController(UserRepository userRepository, CourseRepository courseRepository, BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.blogRepository = blogRepository;
    }

    @GetMapping("/dashboard")
    public String marketingDashboard(Model model) {
        long totalUsers = userRepository.count();
        long totalCourses = courseRepository.count();
        long totalBlogs = blogRepository.count();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalBlogs", totalBlogs);

        return "marketing/dashboard";
    }
}
