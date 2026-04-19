package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.dto.ChapterLearningDTO;

import java.util.List;

public interface LearningService {
    List<ChapterLearningDTO> prepareLearningViewData(long userId, long enrollmentId);
}
