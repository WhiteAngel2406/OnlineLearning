package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.BlogCategory;
import com.swp391.OnlineLearning.Model.dto.BlogDTO;
import com.swp391.OnlineLearning.Service.BlogCategoryService;
import com.swp391.OnlineLearning.Service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    private final BlogService blogService;
    private final BlogCategoryService blogCategoryService;

    public BlogController(BlogService blogService, BlogCategoryService blogCategoryService) {
        this.blogService = blogService;
        this.blogCategoryService = blogCategoryService;
    }

    @GetMapping("/blogs")
    public String viewBlogList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String categorySlug,
            Model model) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BlogDTO> blogPage = blogService.getPaginatedPublishedBlogsByCategorySlug(categorySlug, pageable);
        List<BlogCategory> blogCategories = blogCategoryService.findAll();

        model.addAttribute("blogPage", blogPage);
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("currentPage", page);
        model.addAttribute("categorySlug", categorySlug);

        return "user/viewBlogList";
    }

    @GetMapping("/blogs/{id}")
    public String viewBlogDetails(@PathVariable Long id, Model model) {
        BlogDTO blog = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.findLatestBlogs(4);

        model.addAttribute("blog", blog);
        model.addAttribute("latestBlogs", latestBlogs);

        return "user/viewBlogDetails";
    }
}
