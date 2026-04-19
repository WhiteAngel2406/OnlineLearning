package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Blog;
import com.swp391.OnlineLearning.model.dto.BlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Page<BlogDTO> getPaginatedPublishedBlogsByCategorySlug(String blogCategorySlug, Pageable pageable);

    BlogDTO getBlogById(Long blogId);

    List<BlogDTO> findLatestBlogs(int quantity);
}
