package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.model.CourseCategory;
import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.model.Slider;
import com.example.demo.model.dto.OrderFilter;
import com.example.demo.model.enums.CourseStatus;
import com.example.demo.model.enums.OrderStatus;
import com.example.demo.model.enums.SliderStatus;
import com.example.demo.service.UserService;
import com.example.demo.service.CourseCategoryService;
import com.example.demo.service.CourseService;
import com.example.demo.service.OrderService;
import com.example.demo.service.SliderService;
import com.example.demo.service.UploadService;
import com.example.demo.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final CourseCategoryService categoryService;
    private final CourseService courseService;
    private final OrderService orderService;
    private final SliderService sliderService;
    private final UploadService uploadService;

    public AdminController(UserService userService, RoleRepository roleRepository,
                          CourseCategoryService categoryService, CourseService courseService,
                          OrderService orderService, SliderService sliderService,
                          UploadService uploadService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.categoryService = categoryService;
        this.courseService = courseService;
        this.orderService = orderService;
        this.sliderService = sliderService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("currentUser", user);
        }
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.count());
        stats.put("totalCategories", categoryService.count());
        stats.put("totalCourses", courseService.countCourses());
        stats.put("totalOrders", orderService.count());
        stats.put("totalRevenue", orderService.getTotalRevenue());
        stats.put("pendingCourses", courseService.countByStatus(CourseStatus.PENDING));
        stats.put("approvedCourses", courseService.countByStatus(CourseStatus.APPROVED));
        stats.put("rejectedCourses", courseService.countByStatus(CourseStatus.REJECTED));
        stats.put("pendingOrders", orderService.countByStatus(OrderStatus.PENDING));
        stats.put("successOrders", orderService.countByStatus(OrderStatus.SUCCESS));
        stats.put("failedOrders", orderService.countByStatus(OrderStatus.FAILED));
        model.addAttribute("stats", stats);
        return "admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String getUsers(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> users = userService.findWithFilters(keyword, roleId, enabled, pageable);
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("roleId", roleId);
        model.addAttribute("enabled", enabled);

        return "admin/userList";
    }

    @GetMapping("/admin/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        List<UserRole> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("genders", User.Gender.values());
        return "admin/createUser";
    }

    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute User user, @RequestParam Long roleId, RedirectAttributes ra) {
        try {
            userService.createUser(user, roleId);
            ra.addFlashAttribute("success", "Tạo người dùng thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        List<UserRole> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("genders", User.Gender.values());
        return "admin/updateUser";
    }

    @PostMapping("/admin/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam Long roleId, RedirectAttributes ra) {
        try {
            userService.updateUser(id, user, roleId);
            ra.addFlashAttribute("success", "Cập nhật người dùng thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/toggle/{id}")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes ra) {
        userService.toggleUserStatus(id);
        ra.addFlashAttribute("success", "Thay đổi trạng thái thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        userService.deleteUser(id);
        ra.addFlashAttribute("success", "Xóa người dùng thành công!");
        return "redirect:/admin/users";
    }

    // ==================== COURSE CATEGORY ====================
    @GetMapping("/admin/course_categories")
    public String getCourseCategories(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CourseCategory> categories = categoryService.findWithFilters(keyword, active, pageable);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("active", active);
        return "admin/courseCategory/list";
    }

    @GetMapping("/admin/course_categories/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new CourseCategory());
        return "admin/courseCategory/create";
    }

    @PostMapping("/admin/course_categories/create")
    public String createCategory(@ModelAttribute CourseCategory category, RedirectAttributes ra) {
        try {
            categoryService.createCategory(category);
            ra.addFlashAttribute("success", "Tạo danh mục thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/course_categories";
    }

    @GetMapping("/admin/course_categories/update/{id}")
    public String updateCategoryForm(@PathVariable Long id, Model model) {
        CourseCategory category = categoryService.findById(id);
        if (category != null) {
            model.addAttribute("category", category);
        }
        return "admin/courseCategory/update";
    }

    @PostMapping("/admin/course_categories/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CourseCategory category, RedirectAttributes ra) {
        try {
            categoryService.updateCategory(id, category);
            ra.addFlashAttribute("success", "Cập nhật danh mục thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/course_categories";
    }

    @GetMapping("/admin/course_categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        categoryService.deleteCategory(id);
        ra.addFlashAttribute("success", "Xóa danh mục thành công!");
        return "redirect:/admin/course_categories";
    }

    // ==================== COURSE MANAGEMENT ====================
    @GetMapping("/courses/admin")
    public String courses(@RequestParam(defaultValue = "") String keyword,
                         @RequestParam(required = false) CourseStatus status,
                         @RequestParam(required = false) Long categoryId,
                         @RequestParam(required = false) Long expertId,
                         @RequestParam(required = false) Boolean isFeatured,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> courses = courseService.findForAdmin(keyword, status, categoryId, expertId, isFeatured, pageable);
        model.addAttribute("courses", courses);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("expertId", expertId);
        model.addAttribute("isFeatured", isFeatured);
        return "admin/course/courseDashboard";
    }

    @PostMapping("/courses/admin/approve/{id}")
    public String approveCourse(@PathVariable Long id, RedirectAttributes ra) {
        courseService.approveCourse(id);
        ra.addFlashAttribute("success", "Duyệt khóa học thành công!");
        return "redirect:/courses/admin";
    }

    @PostMapping("/courses/admin/reject/{id}")
    public String rejectCourse(@PathVariable Long id, RedirectAttributes ra) {
        courseService.rejectCourse(id);
        ra.addFlashAttribute("success", "Từ chối khóa học thành công!");
        return "redirect:/courses/admin";
    }

    @PostMapping("/courses/admin/featured/{id}")
    public String toggleFeatured(@PathVariable Long id, RedirectAttributes ra) {
        courseService.toggleFeatured(id);
        ra.addFlashAttribute("success", "Thay đổi nổi bật thành công!");
        return "redirect:/courses/admin";
    }

    // ==================== ORDER MANAGEMENT ====================
    @GetMapping("/admin/orders")
    public String orders(@ModelAttribute OrderFilter filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<Order> orders = orderService.findWithFilters(
            filter.getKeyword(), filter.getStatus(),
            filter.getStartDate(), filter.getEndDate(),
            PageRequest.of(page, size, Sort.by("orderDate").descending())
        );
        model.addAttribute("orders", orders);
        model.addAttribute("filter", filter);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        return "admin/orderDashboard";
    }

    @GetMapping("/admin/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        orderService.findById(id).ifPresent(order -> model.addAttribute("order", order));
        return "admin/orderDetail";
    }

    @PostMapping("/admin/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status, RedirectAttributes ra) {
        orderService.updateOrderStatus(id, status);
        ra.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/orders";
    }

    // ==================== SLIDER MANAGEMENT ====================
    @GetMapping("/admin/sliders")
    public String sliders(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) SliderStatus status,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         Model model) {
        Page<Slider> sliders = sliderService.findWithFilters(keyword, status,
            PageRequest.of(page, size, Sort.by("orderNumber").ascending()));
        model.addAttribute("sliders", sliders);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        return "admin/slider/list";
    }

    @GetMapping("/admin/sliders/create")
    public String createSliderForm(Model model) {
        model.addAttribute("slider", new Slider());
        model.addAttribute("statuses", SliderStatus.values());
        return "admin/slider/create";
    }

    @PostMapping("/admin/sliders/create")
    public String createSlider(@ModelAttribute Slider slider,
                              @RequestParam(required = false) MultipartFile imageFile,
                              RedirectAttributes ra, Model model) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadService.uploadSlider(imageFile);
                slider.setImageUrl(imageUrl);
            }
            sliderService.createSlider(slider);
            ra.addFlashAttribute("success", "Tạo slider thành công!");
            return "redirect:/admin/sliders";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("statuses", SliderStatus.values());
            return "admin/slider/create";
        }
    }

    @GetMapping("/admin/sliders/update/{id}")
    public String updateSliderForm(@PathVariable Long id, Model model) {
        sliderService.findById(id).ifPresent(slider -> model.addAttribute("slider", slider));
        model.addAttribute("statuses", SliderStatus.values());
        return "admin/slider/update";
    }

    @PostMapping("/admin/sliders/update/{id}")
    public String updateSlider(@PathVariable Long id,
                              @ModelAttribute Slider slider,
                              @RequestParam(required = false) MultipartFile imageFile,
                              RedirectAttributes ra, Model model) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadService.uploadSlider(imageFile);
                slider.setImageUrl(imageUrl);
            }
            sliderService.updateSlider(id, slider);
            ra.addFlashAttribute("success", "Cập nhật slider thành công!");
            return "redirect:/admin/sliders";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("statuses", SliderStatus.values());
            return "admin/slider/update";
        }
    }

    @GetMapping("/admin/sliders/delete/{id}")
    public String deleteSlider(@PathVariable Long id, RedirectAttributes ra) {
        sliderService.deleteSlider(id);
        ra.addFlashAttribute("success", "Xóa slider thành công!");
        return "redirect:/admin/sliders";
    }

    @PostMapping("/admin/sliders/approve/{id}")
    public String approveSlider(@PathVariable Long id, RedirectAttributes ra) {
        sliderService.approveSlider(id);
        ra.addFlashAttribute("success", "Duyệt slider thành công!");
        return "redirect:/admin/sliders";
    }

    @PostMapping("/admin/sliders/reject/{id}")
    public String rejectSlider(@PathVariable Long id, RedirectAttributes ra) {
        sliderService.rejectSlider(id);
        ra.addFlashAttribute("success", "Từ chối slider thành công!");
        return "redirect:/admin/sliders";
    }
}
