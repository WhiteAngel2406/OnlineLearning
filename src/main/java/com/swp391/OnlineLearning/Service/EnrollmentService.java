package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.Enrollment;
import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.EnrollmentInfoDTO;
import com.swp391.OnlineLearning.Model.dto.EnrollmentLearningDTO;


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
