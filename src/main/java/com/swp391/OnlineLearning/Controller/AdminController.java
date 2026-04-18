package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Model.CourseCategory;
import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.Slider;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.UserDTO;
import com.swp391.OnlineLearning.Model.enums.OrderStatus;
import com.swp391.OnlineLearning.Model.enums.SliderStatus;
import com.swp391.OnlineLearning.Repository.RoleRepository;
import com.swp391.OnlineLearning.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final OrderService orderService;
    private final SliderService sliderService;
    private final CourseCategoryService courseCategoryService;
    private final CourseService courseService;
    private final RoleRepository roleRepository;
    private final UploadService uploadService;

    //===================== DASHBOARD ========================
    @GetMapping("")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalOrders", orderService.countOrders());
        model.addAttribute("totalSliders", sliderService.countSliders());
        model.addAttribute("totalCourses", courseService.countCourses());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue() != null ? orderService.getTotalRevenue() : 0);
        return "admin/dashboard";
    }

    //===================== USERS ========================
    @GetMapping("/users")
    public String users(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean enabled,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getUsersWithSpecs(pageable, gender, role, enabled, search);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("gender", gender);
        model.addAttribute("role", role);
        model.addAttribute("enabled", enabled);
        return "admin/userList";
    }

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        var roles = roleRepository.findAll();
        model.addAttribute("roles", roles != null ? roles : java.util.Collections.emptyList());
        model.addAttribute("genders", java.util.Arrays.asList(User.Gender.values()));
        return "admin/createUser";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute UserDTO userDTO,
                             @RequestParam Long roleId,
                             @RequestParam User.Gender gender,
                             @RequestParam(required = false) String mobile,
                             @RequestParam(required = false) String address) {
        User user = userService.buildNewUser(userDTO, roleId, gender, mobile, address);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/updateUser";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        User existing = userService.findById(id);
        existing.setFullName(user.getFullName());
        existing.setGender(user.getGender());
        existing.setMobile(user.getMobile());
        existing.setAddress(user.getAddress());
        existing.setEnabled(user.isEnabled());
        userService.save(existing);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        userService.deleteById(id);
        ra.addFlashAttribute("success", "Xóa người dùng thành công!");
        return "redirect:/admin/users";
    }

    //===================== ORDERS ========================
    @GetMapping("/orders")
    public String orders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Order> orders = orderService.findWithFilters(keyword, status, startDate, endDate, pageable);
        model.addAttribute("orders", orders);
        model.addAttribute("filter", new OrderFilter(keyword, status, startDate, endDate));
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        Double totalRevenue = orderService.getTotalRevenue();
        model.addAttribute("totalRevenue", totalRevenue != null ? totalRevenue : 0);
        return "admin/orderDashboard";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        orderService.findById(id).ifPresent(order -> model.addAttribute("order", order));
        return "admin/orderDetail";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/admin/orders/" + id;
    }

    //===================== COURSE CATEGORIES ========================
    @GetMapping("/course_categories")
    public String categories(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<CourseCategory> categories = courseCategoryService.findAll(pageable);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categories.getTotalPages());
        return "admin/courseCategory/list";
    }

    @GetMapping("/course_categories/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new CourseCategory());
        return "admin/courseCategory/create";
    }

    @PostMapping("/course_categories/create")
    public String createCategory(@ModelAttribute CourseCategory category) {
        courseCategoryService.createCategory(category);
        return "redirect:/admin/course_categories";
    }

    @GetMapping("/course_categories/update/{id}")
    public String updateCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", courseCategoryService.findById(id));
        return "admin/courseCategory/update";
    }

    @PostMapping("/course_categories/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CourseCategory category) {
        courseCategoryService.updateCategory(id, category);
        return "redirect:/admin/course_categories";
    }

    @PostMapping("/course_categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        courseCategoryService.deleteCategory(id);
        ra.addFlashAttribute("success", "Xóa danh mục thành công!");
        return "redirect:/admin/course_categories";
    }

    //===================== SLIDERS ========================
    @GetMapping("/sliders")
    public String sliders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) SliderStatus status,
            Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Slider> sliders = sliderService.findWithFilters(keyword, status, pageable);
        model.addAttribute("sliders", sliders);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sliders.getTotalPages());
        return "admin/slider/list";
    }

    @GetMapping("/sliders/create")
    public String createSliderForm(Model model) {
        model.addAttribute("slider", new Slider());
        model.addAttribute("statuses", SliderStatus.values());
        return "admin/slider/create";
    }

    @PostMapping("/sliders/create")
    public String createSlider(@ModelAttribute Slider slider, @RequestParam(required = false) MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = uploadService.uploadSlider(imageFile);
            slider.setImageUrl(imageUrl);
        }
        sliderService.createSlider(slider);
        return "redirect:/admin/sliders";
    }

    @GetMapping("/sliders/update/{id}")
    public String updateSliderForm(@PathVariable Long id, Model model) {
        sliderService.findById(id).ifPresent(slider -> model.addAttribute("slider", slider));
        model.addAttribute("statuses", SliderStatus.values());
        return "admin/slider/update";
    }

    @PostMapping("/sliders/update/{id}")
    public String updateSlider(@PathVariable Long id, @ModelAttribute Slider slider, @RequestParam(required = false) MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = uploadService.uploadSlider(imageFile);
            slider.setImageUrl(imageUrl);
        }
        sliderService.updateSlider(id, slider);
        return "redirect:/admin/sliders";
    }

    @PostMapping("/sliders/approve/{id}")
    public String approveSlider(@PathVariable Long id) {
        sliderService.approveSlider(id);
        return "redirect:/admin/sliders";
    }

    @PostMapping("/sliders/reject/{id}")
    public String rejectSlider(@PathVariable Long id) {
        sliderService.rejectSlider(id);
        return "redirect:/admin/sliders";
    }

    @PostMapping("/sliders/delete/{id}")
    public String deleteSlider(@PathVariable Long id, RedirectAttributes ra) {
        sliderService.deleteSlider(id);
        ra.addFlashAttribute("success", "Xóa slider thành công!");
        return "redirect:/admin/sliders";
    }

    //===================== COURSES ========================
    @GetMapping("/courses/admin")
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

    @PostMapping("/courses/admin/approve/{id}")
    public String approveCourse(@PathVariable Long id) {
        courseService.approveCourse(id);
        return "redirect:/courses/admin";
    }

    @PostMapping("/courses/admin/reject/{id}")
    public String rejectCourse(@PathVariable Long id) {
        courseService.rejectCourse(id);
        return "redirect:/courses/admin";
    }

    @PostMapping("/courses/admin/featured/{id}")
    public String toggleFeaturedCourse(@PathVariable Long id) {
        courseService.toggleFeatured(id);
        return "redirect:/courses/admin";
    }

    // Helper class for order filter
    public static class OrderFilter {
        private String keyword;
        private OrderStatus status;
        private String startDate;
        private String endDate;

        public OrderFilter() {}

        public OrderFilter(String keyword, OrderStatus status, String startDate, String endDate) {
            this.keyword = keyword;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getKeyword() { return keyword; }
        public void setKeyword(String keyword) { this.keyword = keyword; }
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
    }
}
