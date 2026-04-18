package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Enrollment;

public interface UserLessonService {
    void createFullUserLesson(Enrollment enrollment);

    void updateIsCompleted(long userId, long lessonId);

    boolean existsByLessonIdAndUserId(long lessonId, long userId);
}
