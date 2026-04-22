package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.controller.ChapterController;
import com.swp391.OnlineLearning.repository.ChapterRepository;
import com.swp391.OnlineLearning.repository.CourseRepository;
import com.swp391.OnlineLearning.service.ChapterService;
import com.swp391.OnlineLearning.model.Chapter;
import com.swp391.OnlineLearning.model.Course;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService {
    private final CourseRepository courseRepository;
    private final ChapterRepository chapterRepository;

    public ChapterServiceImpl(CourseRepository courseRepository, ChapterRepository chapterRepository) {
        this.courseRepository = courseRepository;
        this.chapterRepository = chapterRepository;
    }

    @Override
    public Chapter createChapterForCourse(Long courseId, ChapterController.CreateChapterRequest createChapterRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Chapter newChapter = new Chapter();
        int orderNumber = course.getChapters().size() + 1;

        newChapter.setName(createChapterRequest.getName());
        newChapter.setShortDescription(createChapterRequest.getShortDescription());
        newChapter.setOrderNumber(orderNumber);
        newChapter.setCourse(course);

        return this.chapterRepository.save(newChapter);
    }

    @Override
    public Optional<Chapter> findById(Long chapterId) {
        return Optional.ofNullable(chapterRepository.findById(chapterId).orElseThrow(() -> new IllegalArgumentException("Chapter not found")));
    }

    @Override
    public void deleteById(Long chapterId) {
        this.chapterRepository.deleteById(chapterId);
    }

    @Override
    @Transactional
    public void deleteChapterAndReorder(Long courseId, Long chapterId) {
        // 1. Kiá»ƒm tra sá»± tá»“n táº¡i (quan trá»ng!)
        if (!chapterRepository.existsById(chapterId)) {
            throw new NoSuchElementException("KhÃ´ng tÃ¬m tháº¥y Chapter vá»›i id: " + chapterId);
        }

        // 2. XÃ³a chapter
        chapterRepository.deleteById(chapterId);

        // 3. Cáº­p nháº­t láº¡i thá»© tá»± (logic nÃ y nÃªn náº±m trong service hoáº·c repository)
        List<Chapter> remainingChapters = chapterRepository.findByCourseIdOrderByOrderNumberAsc(courseId);
        for (int i = 0; i < remainingChapters.size(); i++) {
            Chapter chapter = remainingChapters.get(i);
            chapter.setOrderNumber(i + 1); // Cáº­p nháº­t láº¡i order number thÃ nh 1, 2, 3...
            chapterRepository.save(chapter);
        }
    }

    @Override
    public Chapter updateChapter(Long chapterId, ChapterController.CreateChapterRequest chapter) {
        Chapter updateChapter = this.findById(chapterId).orElseThrow(() -> new IllegalArgumentException("Chapter not found"));
        updateChapter.setName(chapter.getName());
        updateChapter.setShortDescription(chapter.getShortDescription());
        return this.chapterRepository.save(updateChapter);
    }
}
