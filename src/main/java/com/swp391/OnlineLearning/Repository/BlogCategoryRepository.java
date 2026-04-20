package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
}
