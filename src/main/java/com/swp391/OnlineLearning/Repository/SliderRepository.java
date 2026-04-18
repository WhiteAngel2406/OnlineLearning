package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.Model.Slider;
import com.swp391.OnlineLearning.Model.enums.SliderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Long> {

    @Query("SELECT s FROM Slider s WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR s.title LIKE %:keyword%) AND " +
           "(:status IS NULL OR s.status = :status)")
    Page<Slider> findWithFilters(@Param("keyword") String keyword,
                                @Param("status") SliderStatus status,
                                Pageable pageable);

    Page<Slider> findByStatus(SliderStatus status, Pageable pageable);
}
