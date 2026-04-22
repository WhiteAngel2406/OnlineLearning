package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.model.*;
import com.swp391.OnlineLearning.model.dto.ChapterLearningDTO;
import com.swp391.OnlineLearning.model.dto.UserLessonLearningDTO;
import com.swp391.OnlineLearning.repository.EnrollmentRepository;
import com.swp391.OnlineLearning.repository.UserLessonRepository;
import com.swp391.OnlineLearning.repository.UserRepository;
import com.swp391.OnlineLearning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LearningServiceImpl implements LearningService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserLessonRepository userLessonRepository;
    private final UserRepository userRepository;

    public LearningServiceImpl(EnrollmentRepository enrollmentRepository, UserLessonRepository userLessonRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userLessonRepository = userLessonRepository;
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public List<ChapterLearningDTO> prepareLearningViewData(long userId, long enrollmentId) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        // 1. Láº¥y cáº¥u trÃºc chÃ­nh (Enrollment -> Course -> Chapters -> Lessons)
        Enrollment enrollment = enrollmentRepository.findByIdAndUserIdWithCourse(enrollmentId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found!")); // NÃ©m exception cá»¥ thá»ƒ

        Course course = enrollment.getCourse();
        List<Chapter> chapters = course.getChapters();

        // 2. Láº¥y táº¥t cáº£ tiáº¿n Ä‘á»™ há»c cá»§a user cho enrollment nÃ y
        List<UserLesson> userProgressList = userLessonRepository.findAllByEnrollmentIdAndUserIdWithLesson(enrollmentId, userId);

        // 3. Táº¡o Map Ä‘á»ƒ tra cá»©u tiáº¿n Ä‘á»™ nhanh chÃ³ng (Key: lessonId, Value: UserLesson)
        Map<Long, UserLesson> progressMap = userProgressList.stream()
                .collect(Collectors.toMap(ul -> ul.getLesson().getId(), Function.identity()));

        // 4. XÃ¢y dá»±ng cáº¥u trÃºc DTO tráº£ vá»
        List<ChapterLearningDTO> chapterLearningDTOs = new ArrayList<>();
        if (chapters != null) {
            // Sáº¯p xáº¿p chapter theo orderNumber náº¿u cáº§n
            chapters.sort(Comparator.comparingInt(Chapter::getOrderNumber));

            for (Chapter chapter : chapters) {
                List<UserLessonLearningDTO> lessonDTOs = new ArrayList<>();
                List<Lesson> lessons = chapter.getLessons(); // ÄÃ£ Ä‘Æ°á»£c fetch EAGERLY
                if (lessons != null) {
                    // Sáº¯p xáº¿p lesson theo orderNumber náº¿u cáº§n
                    lessons.sort(Comparator.comparingInt(Lesson::getOrderNumber));

                    for (Lesson lesson : lessons) {
                        UserLesson progress = progressMap.get(lesson.getId());
                        lessonDTOs.add(new UserLessonLearningDTO(progress));
                    }
                }
                // Giáº£ sá»­ ChapterLearningDTO cÃ³ constructor phÃ¹ há»£p
                chapterLearningDTOs.add(new ChapterLearningDTO(chapter, lessonDTOs));
            }
        }
        return chapterLearningDTOs;
    }
}
