package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Model.Enrollment;
import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.UserLesson;
import com.swp391.OnlineLearning.Model.dto.EnrollmentInfoDTO;
import com.swp391.OnlineLearning.Model.dto.EnrollmentLearningDTO;
import com.swp391.OnlineLearning.Repository.EnrollmentRepository;
import com.swp391.OnlineLearning.Service.EnrollmentService;
import com.swp391.OnlineLearning.Service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final WishlistService wishlistService;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, WishlistService wishlistService) {
        this.enrollmentRepository = enrollmentRepository;
        this.wishlistService = wishlistService;
    }

    // ================= CREATE ENROLLMENT =================
    @Override
    public Enrollment createNew(Order order) {

        Long userId = order.getUser().getId();
        Long courseId = order.getCourse().getId();

        // Check đã enroll chưa
        if (enrollmentRepository.existsByUserAndCourse(userId, courseId)) {
            throw new IllegalArgumentException("Bạn đã đăng ký khóa học này rồi!");
        }

        // Tạo enrollment
        Enrollment enrollment = enrollmentRepository.save(
                new Enrollment(order.getUser(), order.getCourse())
        );

        wishlistService.findByUserIdAndCourseId(userId, courseId)
                .ifPresent(w -> wishlistService.delete(w.getId()));

        return enrollment;
    }

    // ================= CÁC HÀM KHÁC =================
    @Override
    public List<Enrollment> findByUserId(long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    @Override
    public Enrollment findByIdAndUserIdWithCourse(long enrollmentId, long userId) {
        return enrollmentRepository
                .findByIdAndUserIdWithCourse(enrollmentId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    @Override
    public Enrollment findByIdAndUserIdWithFullCourseStructure(long enrollmentId, long userId) {
        return enrollmentRepository
                .findByIdAndUserIdWithFullCourseStructure(enrollmentId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    @Override
    public Enrollment findByIdWithUserLesson(long enrollmentId) {
        return enrollmentRepository
                .findByIdWithUserLesson(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    @Override
    public EnrollmentLearningDTO createEnrollmentDTO(long enrollmentId) {

        Enrollment enrollment = findByIdWithUserLesson(enrollmentId);
        List<UserLesson> userLessons = enrollment.getUserLessons();

        String title = enrollment.getCourse().getName();

        int totalLessons = userLessons.size();
        int completedLessons = (int) userLessons.stream()
                .filter(UserLesson::isCompleted)
                .count();

        int progress = 0;
        if (totalLessons > 0) {
            progress = (int) Math.round((completedLessons * 100.0) / totalLessons);
        }

        return new EnrollmentLearningDTO(
                enrollmentId,
                title,
                progress,
                totalLessons,
                completedLessons
        );
    }

    @Override
    public List<EnrollmentInfoDTO> createEnrollmentInfoDTO(Long userId) {
        return enrollmentRepository.findEnrollmentInfoByUserId(userId);
    }

    @Override
    public Enrollment findByUserIdAndCourseId(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }
}