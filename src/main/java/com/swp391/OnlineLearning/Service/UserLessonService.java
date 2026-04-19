package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.Enrollment;

public interface UserLessonService {
    void createFullUserLesson(Enrollment enrollment);

    void updateIsCompleted(long userId, long lessonId);

    boolean existsByLessonIdAndUserId(long lessonId, long userId);
}
