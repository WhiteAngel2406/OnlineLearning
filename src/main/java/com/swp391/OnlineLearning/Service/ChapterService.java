package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Controller.ChapterController;
import com.swp391.OnlineLearning.Model.Chapter;

import java.util.Optional;

public interface ChapterService {
    Chapter createChapterForCourse(Long courseId, ChapterController.CreateChapterRequest createChapterRequest);

    Optional<Chapter> findById(Long chapterId);

    void deleteById(Long chapterId);

    void deleteChapterAndReorder(Long courseId, Long chapterId);

    Chapter updateChapter(Long chapterId, ChapterController.CreateChapterRequest chapter);
}
