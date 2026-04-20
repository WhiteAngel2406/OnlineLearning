package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.Note;
import com.swp391.OnlineLearning.Model.dto.NoteDTO;
import com.swp391.OnlineLearning.Model.dto.NoteRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NoteService {
    Note createNew(Long userId, long lessonId, NoteRequest noteRequest);

    List<NoteDTO> createDtosByChapterAndEnrollmentId(long chapterId, long enrollmentId, Sort sort);

    void deleteById(long noteId);

    Note updateById(long noteId, String content);
}
