package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);

    @Query("SELECT w FROM Wishlist w JOIN FETCH w.course WHERE w.user = :user")
    List<Wishlist> findByUserWithCourse(@Param("user") User user);

    Optional<Wishlist> findByUserAndCourse(User user, Course course);
    Optional<Wishlist> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Wishlist> findByUserId(Long userId);
}
