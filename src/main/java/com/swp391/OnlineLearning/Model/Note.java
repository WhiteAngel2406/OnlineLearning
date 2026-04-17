package com.swp391.OnlineLearning.Model;

import com.swp391.OnlineLearning.Model.dto.NoteRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "time_at_lesson")
    private String timeAtLesson;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)", name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_lesson_id")
    private UserLesson userLesson;

    public Note(UserLesson userLesson, NoteRequest noteRequest) {
        this.timeAtLesson = noteRequest.getTimeAtLesson();
        this.content = noteRequest.getContent();
        this.userLesson = userLesson;
    }

    public Note(String timeAtLesson, String content, UserLesson userLesson) {
        this.timeAtLesson = timeAtLesson;
        this.content = content;
        this.userLesson = userLesson;
    }

}
