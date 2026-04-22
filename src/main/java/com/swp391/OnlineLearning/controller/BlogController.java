package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.model.BlogCategory;
import com.swp391.OnlineLearning.model.dto.BlogDTO;
import com.swp391.OnlineLearning.service.BlogCategoryService;
import com.swp391.OnlineLearning.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogCategoryService blogCategoryService;

    @GetMapping("")
    public String getBlogs(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "categorySlug", required = false) String categorySlug,
                           Model model) {
        try{
            Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable pageable = PageRequest.of(page-1, 5, sort);

            Page<BlogDTO> blogPage = this.blogService.getPaginatedPublishedBlogsByCategorySlug(categorySlug, pageable);
            List<BlogCategory> blogCategories = this.blogCategoryService.findAll();

            if (categorySlug != null && !categorySlug.isEmpty()) {
                blogCategories.stream()
                        .filter(c -> categorySlug.equals(c.getSlug()))
                        .findFirst()
                        .ifPresent(c -> model.addAttribute("activeCategory", c));
            }

            List<BlogDTO> content = blogPage.getContent();
            if (page == 1 && !content.isEmpty()) {
                model.addAttribute("featuredBlog", content.get(0));
                if (content.size() > 1) {
                    model.addAttribute("regularBlogs", content.subList(1, content.size()));
                } else {
                    model.addAttribute("regularBlogs", List.of());
                }
            } else {
                model.addAttribute("regularBlogs", content);
            }

            model.addAttribute("blogPage", blogPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("blogCategories", blogCategories);
            model.addAttribute("categorySlug", categorySlug);
            return "user/viewBlogList";
        }catch (Exception e){
            return "redirect:/";
        }
    }

    @GetMapping("/{blogId}")
    public String getBlogDetailsPage(@PathVariable("blogId") Long blogId,
                                     Model model){
        try{
            BlogDTO blogDTO = this.blogService.getBlogById(blogId);
            model.addAttribute("blog", blogDTO);
            return "user/viewBlogDetails";
        }catch (Exception e){
            return "redirect:/";
        }
    }
}
