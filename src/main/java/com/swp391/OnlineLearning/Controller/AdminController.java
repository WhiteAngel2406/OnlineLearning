package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.model.Course;
import com.swp391.OnlineLearning.model.CourseCategory;
import com.swp391.OnlineLearning.model.Order;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.UserRole;
import com.swp391.OnlineLearning.model.dto.OrderFilter;
import com.swp391.OnlineLearning.repository.CourseRepository;
import com.swp391.OnlineLearning.repository.EnrollmentRepository;
import com.swp391.OnlineLearning.repository.OrderRepository;
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

import javax.management.relation.Role;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UploadService uploadService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CourseCategoryService courseCategoryService;
    private final OrderService orderService;
    private final CourseRepository courseRepository;
    private final OrderRepository orderRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AdminController(UserService userService, UploadService uploadService, RoleService roleService, PasswordEncoder passwordEncoder, EmailService emailService, CourseCategoryService courseCategoryService, OrderService orderService, CourseRepository courseRepository, OrderRepository orderRepository, EnrollmentRepository enrollmentRepository) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.courseCategoryService = courseCategoryService;
        this.orderService = orderService;
        this.courseRepository = courseRepository;
        this.orderRepository = orderRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    //===================== DASHBOARD ========================
    @GetMapping("")
    public String admin(Model model) {
        List<User> users = userService.getAllUsers();
        List<Order> orders = orderService.getAllOrders();
        List<Course> courses = courseRepository.findAll();

        // Counts
        long totalUsers = users.size();
        long totalCourses = courses.size();
        long totalOrders = orders.size();

        // Revenue
        double totalRevenue = orders.stream()
                .mapToDouble(Order::getAmount)
                .sum();

        // Monthly revenue (last 6 months)
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        java.time.LocalDate now = java.time.LocalDate.now();
        for (int i = 5; i >= 0; i--) {
            java.time.LocalDate month = now.minusMonths(i);
            String monthKey = month.getMonth().toString().substring(0, 3);
            double revenue = orders.stream()
                    .filter(o -> o.getUpdatedAt() != null)
                    .filter(o -> {
                        java.time.LocalDate orderMonth = o.getUpdatedAt().toLocalDate();
                        return orderMonth.getMonth() == month.getMonth() && orderMonth.getYear() == month.getYear();
                    })
                    .mapToDouble(Order::getAmount)
                    .sum();
            monthlyRevenue.put(monthKey, revenue);
        }

        // Popular courses (by enrollment count)
        List<Map<String, Object>> popularCourses = new ArrayList<>();
        for (Course course : courses) {
            long enrollmentCount = enrollmentRepository.countByCourseId(course.getId());
            Map<String, Object> courseData = new HashMap<>();
            courseData.put("course", course);
            courseData.put("enrollmentCount", enrollmentCount);
            popularCourses.add(courseData);
        }
        popularCourses.sort((a, b) -> Long.compare((Long) b.get("enrollmentCount"), (Long) a.get("enrollmentCount")));
        if (popularCourses.size() > 5) popularCourses = popularCourses.subList(0, 5);

        // Recent orders (latest 5)
        List<Order> recentOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // Recent courses (latest 5)
        List<Course> recentCourses = courses.stream()
                .sorted(Comparator.comparing(Course::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalRevenue", new DecimalFormat("#,###").format(totalRevenue) + "Ä‘");
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("popularCourses", popularCourses);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("recentCourses", recentCourses);

        return "admin/dashboard";
    }


    @GetMapping("/users")
    public String getUsers(Model model,
                           @RequestParam(value = "role", required = false) String role,
                           @RequestParam(value = "gender", required = false) String gender,
                           @RequestParam(value = "enabled", required = false) Boolean enabled,
                           @RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           @RequestParam(value = "sort", defaultValue = "id") String sort,
                           @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        // Táº¡o Sort object
        Sort sortObj = createSort(sort, direction);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<User> userPage = this.userService.getUsersWithSpecs(pageable, gender, role, enabled, search);

        model.addAttribute("userPage", userPage);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("genders", User.Gender.values());
        return "admin/userList";
    }

    private Sort createSort(String sort, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        switch (sort) {
            case "fullName":
                return Sort.by(sortDirection, "fullName");
            case "gender":
                return Sort.by(sortDirection, "gender");
            case "email":
                return Sort.by(sortDirection, "email");
            case "mobile":
                return Sort.by(sortDirection, "mobile");
            case "role":
                return Sort.by(sortDirection, "role.name");
            default: // id
                return Sort.by(sortDirection, "id");
        }
    }

    //===================== CREATE USER ========================
    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("genders", User.Gender.values());
        List<UserRole> roles = roleService.findAll();
        roles.removeIf(role -> role.getName().equals("ROLE_USER"));
        model.addAttribute("roles", roles);
        return "admin/createUser";
    }

    @PostMapping("/users/create")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", User.Gender.values());
            List<UserRole> roles = roleService.findAll();
            roles.removeIf(role -> role.getName().equals("ROLE_USER"));
            model.addAttribute("roles", roles);
            return "admin/createUser";
        }

        try {
            userService.ensureEmailNotExists(user.getEmail());

            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarFileName = uploadService.uploadImage(avatarFile, "avatars");
                user.setAvatar(avatarFileName);
            }
            String plainPassword = user.getPassword();
            // Encode password and set default values
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setEnabled(true);

            // Save user
            userService.save(user);

            // Send email notification
            emailService.sendEmail(user.getEmail(), "TÃ i khoáº£n Ä‘Ã£ Ä‘Æ°á»£c táº¡o bá»Ÿi Quáº£n trá»‹ viÃªn",
                    emailService.buildEmailContent(plainPassword));

            redirectAttributes.addFlashAttribute("successMessage", "Táº¡o ngÆ°á»i dÃ¹ng thÃ nh cÃ´ng!");
            return "redirect:/admin/users";

        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            model.addAttribute("genders", User.Gender.values());
            model.addAttribute("roles", roleService.findAll());
            return "admin/createUser";
        } catch (Exception e) {
            bindingResult.rejectValue("email", "error.user", "ÄÃ£ xáº£y ra lá»—i khi táº¡o ngÆ°á»i dÃ¹ng: " + e.getMessage());
            model.addAttribute("genders", User.Gender.values());
            model.addAttribute("roles", roleService.findAll());
            return "admin/createUser";
        }
    }

    //===================== UPDATE USER ========================
    @GetMapping("/users/update/{id}")
    public String getViewAndUpdateForm(@PathVariable("id") Long id, Model model){
        try{
            User user = this.userService.getUserById(id);
            model.addAttribute("user", user);
            List<UserRole> roles = roleService.findAll();
            roles.removeIf(role -> role.getName().equals("ROLE_USER"));
            model.addAttribute("roles", roles);
            return "admin/updateUser";
        }catch(Exception e){
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/update")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/updateUser";
        }
        try{
            User currentUser = this.userService.getUserById(user.getId());
            currentUser.setRole(user.getRole());
            currentUser.setEnabled(user.isEnabled());
            this.userService.save(currentUser);

            redirectAttributes.addFlashAttribute("successMessage", "Cáº­p nháº­t ngÆ°á»i dÃ¹ng thÃ nh cÃ´ng!");
            return "redirect:/admin/users";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Lá»—i khi cáº­p nháº­t ngÆ°á»i dÃ¹ng: " + e.getMessage());
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            this.userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "XÃ³a ngÆ°á»i dÃ¹ng thÃ nh cÃ´ng!");
            return "redirect:/admin/users";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Lá»—i khi xÃ³a ngÆ°á»i dÃ¹ng: " + e.getMessage());
            return "redirect:/admin/users";
        }
    }

    //===================== COURSE CATEGORY ===============================
    @GetMapping("/course_categories")
    public String getCourseCategories(Model model,
                                      @RequestParam(value = "active", required = false) Boolean active,
                                      @RequestParam(value = "search", required = false) String search,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size);

        Page<CourseCategory> courseCategoryPage = this.courseCategoryService.getCourseCategoriesWithSpecs(pageable, active, search);

        model.addAttribute("courseCategoryPage", courseCategoryPage);

        int totalPages = courseCategoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/courseCategory/list";
    }

    @GetMapping("/course_categories/create")
    public String createCourseCategoryForm(Model model) {
        model.addAttribute("courseCategory", new CourseCategory());
        return "admin/courseCategory/create";
    }

    @PostMapping("/course_categories/create")
    public String createCourseCategory(@Valid @ModelAttribute("courseCategory") CourseCategory courseCategory,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/courseCategory/create";
        }
        try{
            courseCategoryService.ensureNotDuplicateName(courseCategory.getName());
            courseCategoryService.save(courseCategory);
            return "redirect:/admin/course_categories";
        }catch (Exception e) {
            bindingResult.rejectValue("name", "error.courseCategory", e.getMessage());
            return "redirect:/admin/course_categories";
        }
    }

    @GetMapping("/course_categories/update/{id}")
    public String createCourseCategoryForm(@PathVariable("id") Long id, Model model,
                                           RedirectAttributes redirectAttributes) {
        try{
            CourseCategory cc = this.courseCategoryService.getById(id);
            model.addAttribute("courseCategory", cc);
            return "admin/courseCategory/update";
        }catch (Exception e) {
            return "redirect:/admin/course_categories";
        }
    }

    @PostMapping("course_categories/update")
    public String updateCourseCategory(@Valid @ModelAttribute("courseCategory") CourseCategory courseCategory,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/courseCategory/update";
        }
        try{
            CourseCategory cc = this.courseCategoryService.getById(courseCategory.getId());
            this.courseCategoryService.ensureNotDuplicateName(courseCategory.getName());

            cc.setName(courseCategory.getName());
            cc.setDescription(courseCategory.getDescription());
            cc.setActive(courseCategory.isActive());
            this.courseCategoryService.save(cc);

            return "redirect:/admin/course_categories";
        }catch (Exception e) {
            bindingResult.rejectValue("name", "error.courseCategory", e.getMessage());
            return "redirect:/admin/course_categories";
        }
    }

    @GetMapping("/orders")
    public String orderManagement(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(required = false, defaultValue = "") String status,
                                  @RequestParam(required = false, defaultValue = "updatedAt") String sortBy,
                                  @RequestParam(required = false, defaultValue = "DESC") String sortDir,
                                  @RequestParam(required = false, defaultValue = "") String keyword,
                                  @RequestParam(required = false,defaultValue = "line" )String chartType,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startUpdate,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endUpdate){
        OrderFilter filter = new OrderFilter();
        filter.setStatus(status);
        filter.setSortBy(sortBy);
        filter.setSortDir(sortDir);
        filter.setSearch(keyword);
        if(startUpdate != null){
            filter.setStartUpdate(startUpdate.atStartOfDay());
        }
        if(endUpdate != null){
            filter.setEndUpdate(endUpdate.atStartOfDay().plusDays(1));
        }

        List<Order> orders = orderService.getOrdersWithSpecs(filter);
        //List to page
        int start = Math.min(page * size, orders.size());
        int end = Math.min((page + 1) * size, orders.size());
        List<Order> pagedOrders = orders.subList(start, end);
        Page<Order> ordersPage = new PageImpl<>(pagedOrders, PageRequest.of(page, size), orders.size());
        //total amount
        double totalAmount = orders.stream()
                .mapToDouble(Order::getAmount)
                .sum();

        // TÃ­nh doanh thu theo thÃ¡ng (YearMonth)
        Map<YearMonth, Double> revenueByMonth = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> YearMonth.from(order.getUpdatedAt()),
                        TreeMap::new,
                        Collectors.summingDouble(Order::getAmount)
                ));

        // Chuyá»ƒn sang 2 danh sÃ¡ch Ä‘á»ƒ hiá»ƒn thá»‹ trÃªn biá»ƒu Ä‘á»“
        List<String> monthLabels = revenueByMonth.keySet().stream()
                .map(ym -> ym.toString())
                .toList();
        List<Double> monthTotals = new ArrayList<>(revenueByMonth.values());

        model.addAttribute("startUpdate", startUpdate);
        model.addAttribute("endUpdate", endUpdate);
        model.addAttribute("orders", ordersPage);
        model.addAttribute("allStatuses", Order.OrderStatus.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("filter", filter);
        model.addAttribute("chartType", chartType);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("allOrders", orders);
        model.addAttribute("monthLabels", monthLabels);
        model.addAttribute("monthTotals", monthTotals);
        return "admin/orderDashboard";
    }

    @GetMapping("/orders/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model){
        Order order = orderService.getOrderById(orderId);
        if(order == null){
            return "redirect:/admin/orders";
        }
        model.addAttribute("order", order);
        return "admin/orderDetail";
    }
}
