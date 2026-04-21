package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Repository.CourseRepository;
import com.swp391.OnlineLearning.Repository.UserRepository;
import com.swp391.OnlineLearning.Repository.WishlistRepository;
import com.swp391.OnlineLearning.Service.WishlistService;
import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository,
                               UserRepository userRepository,
                               CourseRepository courseRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Wishlist createNew(Long userId, Long courseId) {

        if (wishlistRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new IllegalArgumentException("Course already in wishlist");
        }

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Course currentCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        return wishlistRepository.save(new Wishlist(currentUser, currentCourse));
    }

    @Override
    public List<Wishlist> findByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public void delete(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }

    @Override
    public Optional<Wishlist> findByUserIdAndCourseId(Long userId, Long courseId) {
        return wishlistRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public void removeByUserAndCourse(Long userId, Long courseId) {
        wishlistRepository.deleteByUserIdAndCourseId(userId, courseId);
    }
}