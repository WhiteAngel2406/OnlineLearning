package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question extends BaseEntity{
    public enum QuestionType {
        MULTIPLE_CHOICE("Tráº¯c nghiá»‡m nhiá»u lá»±a chá»n"),
        SHORT_ANSWER("Äiá»n tá»«/cÃ¢u tráº£ lá»i ngáº¯n");
        private final String displayName;
        QuestionType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }

    public enum MediaType {
        NONE,
        IMAGE,
        AUDIO,
        VIDEO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    @NotBlank(message = "Ná»™i dung cÃ¢u há»i khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    private String content; // Ná»™i dung text cá»§a cÃ¢u há»i

    @Column(nullable = false)
    @NotNull(message = "Loáº¡i cÃ¢u há»i khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType; // Loáº¡i cÃ¢u há»i (vÃ­ dá»¥: tráº¯c nghiá»‡m, tá»± luáº­n...)

    // --- Pháº§n dÃ nh cho Media ---
    @NotNull(message = "Loáº¡i phÆ°Æ¡ng tiá»‡n (media) khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng, vÃ­ dá»¥: NONE.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaType mediaType; // Loáº¡i media Ä‘Ã­nh kÃ¨m (náº¿u cÃ³)

    @Size(max = 2048, message = "ÄÆ°á»ng dáº«n media khÃ´ng Ä‘Æ°á»£c vÆ°á»£t quÃ¡ 2048 kÃ½ tá»±.")
    @Column(length = 2048, columnDefinition = "NVARCHAR(MAX)") // Giá»›i háº¡n Ä‘á»™ dÃ i trong CSDL
    private String mediaUrl; // ÄÆ°á»ng dáº«n tá»›i file media

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private ShortAnswerOption shortAnswerOption;

    public Question() {
        super();
    }
    public Question(String content, QuestionType questionType, MediaType mediaType, String mediaUrl, Lesson lesson) {
        this.content = content;
        this.questionType = questionType;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.lesson = lesson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public ShortAnswerOption getShortAnswerOption() {
        return shortAnswerOption;
    }

    public void setShortAnswerOption(ShortAnswerOption shortAnswerOption) {
        this.shortAnswerOption = shortAnswerOption;
    }
}
