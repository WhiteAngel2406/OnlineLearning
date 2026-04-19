package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.UserLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
    Optional<UserLesson> findByUserIdAndLessonId(long userId, long lessonId);

    @Query("SELECT ul FROM UserLesson ul JOIN FETCH ul.lesson " +
            "WHERE ul.enrollment.id = :enrollmentId AND ul.user.id = :userId")
    List<UserLesson> findAllByEnrollmentIdAndUserIdWithLesson(@Param("enrollmentId") long enrollmentId, @Param("userId") long userId);
}
