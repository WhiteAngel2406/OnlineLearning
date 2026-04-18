package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.dto.ChapterLearningDTO;

import java.util.List;

public interface LearningService {
    List<ChapterLearningDTO> prepareLearningViewData(long userId, long enrollmentId);
}
