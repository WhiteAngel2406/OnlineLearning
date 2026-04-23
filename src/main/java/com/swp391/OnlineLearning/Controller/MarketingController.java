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
    private final com.swp391.OnlineLearning.Repository.OrderRepository orderRepository;

    public MarketingController(UserRepository userRepository, CourseRepository courseRepository, BlogRepository blogRepository, com.swp391.OnlineLearning.Repository.OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.blogRepository = blogRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/dashboard")
    public String marketingDashboard(Model model) {
        long totalUsers = userRepository.count();
        long totalCourses = courseRepository.count();
        long totalBlogs = blogRepository.count();

        Double totalRevenue = orderRepository.getTotalRevenue();
        if (totalRevenue == null) totalRevenue = 0.0;

        java.util.List<Object[]> categoryStats = courseRepository.countCoursesByCategory();
        java.util.List<String> categoryLabels = new java.util.ArrayList<>();
        java.util.List<Long> categoryData = new java.util.ArrayList<>();
        for (Object[] row : categoryStats) {
            categoryLabels.add((String) row[0]);
            categoryData.add((Long) row[1]);
        }

        java.util.List<com.swp391.OnlineLearning.Model.Course> topCourses = courseRepository.findTop5ByOrderByCreatedAtDesc();
        
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 5, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt"));
        java.util.List<com.swp391.OnlineLearning.Model.dto.BlogDTO> recentBlogs = blogRepository.findLatestPublishedBlogs(pageable);

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalBlogs", totalBlogs);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("categoryLabels", categoryLabels);
        model.addAttribute("categoryData", categoryData);
        model.addAttribute("topCourses", topCourses);
        model.addAttribute("recentBlogs", recentBlogs);

        return "marketing/dashboard";
    }
}
