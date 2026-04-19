package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword% OR c.description LIKE %:keyword%) " +
           "AND (:status IS NULL OR c.status = :status) " +
           "AND (:categoryId IS NULL OR c.category.id = :categoryId) " +
           "AND (:expertId IS NULL OR c.author.id = :expertId) " +
           "AND (:isFeatured IS NULL OR c.featured = :isFeatured)")
    Page<Course> findForAdmin(@Param("keyword") String keyword,
                              @Param("status") Course.CourseStatus status,
                              @Param("categoryId") Long categoryId,
                              @Param("expertId") Long expertId,
                              @Param("isFeatured") Boolean isFeatured,
                              Pageable pageable);

    Page<Course> findByAuthorId(Long authorId, Pageable pageable);

    Page<Course> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Course> findByStatus(Course.CourseStatus status, Pageable pageable);
}
