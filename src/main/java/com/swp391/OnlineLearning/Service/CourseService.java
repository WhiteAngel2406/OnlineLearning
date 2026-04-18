package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Page<Course> findForAdmin(String keyword, Course.CourseStatus status, Long categoryId,
                                     Long expertId, Boolean isFeatured, Pageable pageable) {
        return courseRepository.findForAdmin(keyword, status, categoryId, expertId, isFeatured, pageable);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Page<Course> findByAuthorId(Long expertId, Pageable pageable) {
        return courseRepository.findByAuthorId(expertId, pageable);
    }

    public Page<Course> findByCategoryId(Long categoryId, Pageable pageable) {
        return courseRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<Course> findByStatus(Course.CourseStatus status, Pageable pageable) {
        return courseRepository.findByStatus(status, pageable);
    }

    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        course.setName(courseDetails.getName());
        course.setDescription(courseDetails.getDescription());
        course.setThumbnail(courseDetails.getThumbnail());
        course.setPrice(courseDetails.getPrice());
        course.setCategory(courseDetails.getCategory());

        return courseRepository.save(course);
    }

    @Transactional
    public Course approveCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        course.setStatus(Course.CourseStatus.PUBLISHED);
        return courseRepository.save(course);
    }

    @Transactional
    public Course rejectCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        course.setStatus(Course.CourseStatus.DRAFT);
        return courseRepository.save(course);
    }

    @Transactional
    public Course toggleFeatured(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        course.setFeatured(!course.isFeatured());
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        course.setStatus(Course.CourseStatus.DRAFT);
        courseRepository.save(course);
    }

    public long countCourses() {
        return courseRepository.count();
    }

    public long countByStatus(Course.CourseStatus status) {
        return courseRepository.findByStatus(status, Pageable.unpaged()).getTotalElements();
    }

    public long countByExpert(Long expertId) {
        return courseRepository.findByAuthorId(expertId, Pageable.unpaged()).getTotalElements();
    }
}
