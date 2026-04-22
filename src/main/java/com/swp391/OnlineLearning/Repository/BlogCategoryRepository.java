package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
}
