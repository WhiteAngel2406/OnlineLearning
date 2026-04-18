package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.Model.CourseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {

    Optional<CourseCategory> findByName(String name);

    @Query("SELECT c FROM CourseCategory c WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword%) " +
           "AND (:active IS NULL OR c.active = :active)")
    Page<CourseCategory> findWithFilters(@Param("keyword") String keyword,
                                        @Param("active") Boolean active,
                                        Pageable pageable);

    boolean existsByName(String name);
}
