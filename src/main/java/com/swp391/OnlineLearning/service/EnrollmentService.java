package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Enrollment;
import com.swp391.OnlineLearning.model.Order;
import com.swp391.OnlineLearning.model.dto.EnrollmentInfoDTO;
import com.swp391.OnlineLearning.model.dto.EnrollmentLearningDTO;

import java.util.List;

public interface EnrollmentService {
    Enrollment createNew(Order order);

    List<Enrollment> findByUserId(long userId);

    Enrollment findByIdAndUserIdWithCourse(long enrollmentId, long userId);

    Enrollment findByIdAndUserIdWithFullCourseStructure(long enrollmentId, long userId);

    Enrollment findByIdWithUserLesson(long enrollmentId);

    EnrollmentLearningDTO createEnrollmentDTO(long enrollmentId);

    List<EnrollmentInfoDTO> createEnrollmentInfoDTO(Long userId);

    Enrollment findByUserIdAndCourseId(Long userId, Long courseId);
}
