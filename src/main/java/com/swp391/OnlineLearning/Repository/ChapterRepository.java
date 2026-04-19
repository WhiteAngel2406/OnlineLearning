package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByCourseIdOrderByOrderNumberAsc(Long courseId);
}
