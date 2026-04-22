package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Enrollment;
import com.swp391.OnlineLearning.model.Lesson;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.dto.UserLessonLearningDTO;

import java.util.List;

public interface UserLessonService {
    void createFullUserLesson(Enrollment enrollment);

    void updateIsCompleted(long userId, long lessonId);

    boolean existsByLessonIdAndUserId(long lessonId, long userId);
}
