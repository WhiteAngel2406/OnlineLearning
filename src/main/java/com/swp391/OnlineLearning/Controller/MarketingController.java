package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Repository.BlogRepository;
import com.swp391.OnlineLearning.Repository.CourseRepository;
import com.swp391.OnlineLearning.Repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/marketing")
public class MarketingController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BlogRepository blogRepository;
    private final com.swp391.OnlineLearning.Repository.OrderRepository orderRepository;

    @org.springframework.beans.factory.annotation.Autowired
    private com.swp391.OnlineLearning.Repository.BlogCategoryRepository blogCategoryRepository;

    @org.springframework.beans.factory.annotation.Autowired
    private com.swp391.OnlineLearning.Service.UploadService uploadService;

    public MarketingController(UserRepository userRepository, CourseRepository courseRepository, BlogRepository blogRepository, com.swp391.OnlineLearning.Repository.OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.blogRepository = blogRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/dashboard")
    public String marketingDashboard(Model model) {
        long totalLearners = userRepository.countByRole_Name("ROLE_USER");
        long totalExperts = userRepository.countByRole_Name("ROLE_EXPERT");
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

        model.addAttribute("totalLearners", totalLearners);
        model.addAttribute("totalExperts", totalExperts);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalBlogs", totalBlogs);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("categoryLabels", categoryLabels);
        model.addAttribute("categoryData", categoryData);
        model.addAttribute("topCourses", topCourses);
        model.addAttribute("recentBlogs", recentBlogs);

        return "marketing/dashboard";
    }

    @GetMapping("/blogs")
    public String viewBlogList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        // Normalize empty strings to null
        if (keyword != null && keyword.trim().isEmpty()) {
            keyword = null;
        }
        com.swp391.OnlineLearning.Model.Blog.BlogStatus blogStatus = null;
        if (status != null && !status.trim().isEmpty()) {
            try {
                blogStatus = com.swp391.OnlineLearning.Model.Blog.BlogStatus.valueOf(status);
            } catch (IllegalArgumentException ignored) {
            }
        }
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(
                page, size, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt"));
        org.springframework.data.domain.Page<com.swp391.OnlineLearning.Model.Blog> blogPage = blogRepository.searchBlogs(keyword, blogStatus, pageable);
        model.addAttribute("blogs", blogPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());
        model.addAttribute("statuses", com.swp391.OnlineLearning.Model.Blog.BlogStatus.values());
        return "marketing/blogList";
    }

    @GetMapping("/blogs/{id}")
    public String viewBlogDetails(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        com.swp391.OnlineLearning.Model.Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return "redirect:/marketing/blogs";
        }
        model.addAttribute("blog", blog);
        return "marketing/blogDetails";
    }

    @GetMapping("/blogs/create")
    public String showCreateBlogForm(Model model) {
        java.util.List<com.swp391.OnlineLearning.Model.BlogCategory> categories = blogCategoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "marketing/blogCreate";
    }

    @PostMapping("/blogs/create")
    public String createBlog(
            @RequestParam("title") String title,
            @RequestParam("shortDescription") String shortDescription,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Long userId = (Long) session.getAttribute("currentUserId");
            if (userId == null) {
                redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập lại.");
                return "redirect:/marketing/blogs";
            }
            com.swp391.OnlineLearning.Model.User author = userRepository.findById(userId).orElse(null);
            com.swp391.OnlineLearning.Model.BlogCategory category = blogCategoryRepository.findById(categoryId).orElse(null);

            if (author == null || category == null) {
                redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ.");
                return "redirect:/marketing/blogs";
            }

            com.swp391.OnlineLearning.Model.Blog blog = new com.swp391.OnlineLearning.Model.Blog();
            blog.setTitle(title);
            blog.setShortDescription(shortDescription);
            blog.setContent(content);
            blog.setBlogCategory(category);
            blog.setAuthor(author);
            blog.setStatus(com.swp391.OnlineLearning.Model.Blog.BlogStatus.DRAFT);

            if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
                String fileName = uploadService.uploadImage(thumbnailFile, "blogs");
                blog.setThumbnail(fileName);
            } else {
                blog.setThumbnail(""); // prevent null
            }

            blogRepository.save(blog);
            redirectAttributes.addFlashAttribute("success", "Đã tạo bài viết chờ duyệt thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/marketing/blogs";
    }

    @PostMapping("/blogs/delete/{id}")
    public String deleteBlog(@org.springframework.web.bind.annotation.PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            com.swp391.OnlineLearning.Model.Blog blog = blogRepository.findById(id).orElse(null);
            if (blog != null && blog.getStatus() == com.swp391.OnlineLearning.Model.Blog.BlogStatus.DRAFT) {
                blogRepository.delete(blog);
                redirectAttributes.addFlashAttribute("success", "Đã xóa bài viết thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa bài viết này!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa: " + e.getMessage());
        }
        return "redirect:/marketing/blogs";
    }
}
