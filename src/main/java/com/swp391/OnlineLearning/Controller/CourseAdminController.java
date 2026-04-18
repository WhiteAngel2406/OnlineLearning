package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Service.CourseCategoryService;
import com.swp391.OnlineLearning.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseAdminController {

    private final CourseService courseService;
    private final CourseCategoryService courseCategoryService;

    @GetMapping("/admin")
    public String courses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Course.CourseStatus status,
            @RequestParam(required = false) Long categoryId,
            Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Course> courses = courseService.findForAdmin(keyword, status, categoryId, null, null, pageable);
        model.addAttribute("courses", courses);
        model.addAttribute("categories", courseCategoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", courses.getTotalPages());
        return "admin/course/courseDashboard";
    }

    @PostMapping("/admin/approve/{id}")
    public String approveCourse(@PathVariable Long id) {
        courseService.approveCourse(id);
        return "redirect:/courses/admin";
    }

    @PostMapping("/admin/reject/{id}")
    public String rejectCourse(@PathVariable Long id) {
        courseService.rejectCourse(id);
        return "redirect:/courses/admin";
    }

    @PostMapping("/admin/featured/{id}")
    public String toggleFeaturedCourse(@PathVariable Long id) {
        courseService.toggleFeatured(id);
        return "redirect:/courses/admin";
    }
}
