package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.Slider;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Long> {

    // TÃ¬m kiáº¿m theo tiÃªu Ä‘á» hoáº·c linkUrl
    @Query("SELECT s FROM Slider s WHERE " +
            "(LOWER(s.title) LIKE LOWER(CONCAT('%', COALESCE(:keyword, ''), '%')) OR " +
            "LOWER(s.linkUrl) LIKE LOWER(CONCAT('%', COALESCE(:keyword, ''), '%'))) AND " +
            "(:status IS NULL OR s.status = :status)")
    Page<Slider> searchSliders(@Param("keyword") String keyword,
                               @Param("status") String status,
                               Pageable pageable);

    // Láº¥y slider theo status vÃ  sáº¯p xáº¿p theo orderNumber
    List<Slider> findByStatusOrderByOrderNumberAsc(String status);
}


