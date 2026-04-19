package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByCourseIdOrderByOrderNumberAsc(Long courseId);
}
