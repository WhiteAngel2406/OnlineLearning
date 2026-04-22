package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
public class Lesson extends BaseEntity{
    public enum LessonType {
        LECTURE("BÃ i giáº£ng"),
        QUIZ("BÃ i kiá»ƒm tra");

        private final String displayName;
        LessonType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200, columnDefinition = "NVARCHAR(200)")
    @NotBlank(message = "TiÃªu Ä‘á» bÃ i há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(min = 5, max = 200, message = "TiÃªu Ä‘á» bÃ i há»c pháº£i cÃ³ Ä‘á»™ dÃ i tá»« 5 Ä‘áº¿n 200 kÃ½ tá»±.")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "Thá»© tá»± bÃ i há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @PositiveOrZero(message = "Thá»© tá»± bÃ i há»c pháº£i lÃ  sá»‘ khÃ´ng Ã¢m.")
    private Integer orderNumber;

    @Column(nullable = false)
    @NotNull(message = "Loáº¡i bÃ i há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @Positive(message = "Thá»i lÆ°á»£ng Æ°á»›c tÃ­nh cá»§a bÃ i há»c pháº£i lÃ  sá»‘ dÆ°Æ¡ng.")
    private Integer estimatedTime;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String htmlContent;

    // --- DÃ nh cho bÃ i giáº£ng ---
    private String videoUrl;
    private Long duration = 0L; // tÃ­nh báº±ng milliseconds

    // --- DÃ nh cho bÃ i kiá»ƒm tra ---
    @Column(name = "pass_rate")
    @Min(value = 0, message = "Äiá»ƒm Ä‘áº¡t yÃªu cáº§u pháº£i lá»›n hÆ¡n hoáº·c báº±ng 0.")
    @Max(value = 100, message = "Äiá»ƒm Ä‘áº¡t yÃªu cáº§u pháº£i nhá» hÆ¡n hoáº·c báº±ng 100.")
    private Integer passRate;

    @Positive(message = "Giá»›i háº¡n thá»i gian pháº£i lÃ  sá»‘ dÆ°Æ¡ng.")
    private Integer timeLimitInMinutes;

    @Column(name = "number_of_questions")
    @Min(value = 1, message = "Sá»‘ lÆ°á»£ng cÃ¢u há»i pháº£i lá»›n hÆ¡n hoáº·c báº±ng 1.")
    @Max(value = 100, message = "Sá»‘ lÆ°á»£ng cÃ¢u há»i pháº£i nhá» hÆ¡n hoáº·c báº±ng 100.")
    private Integer numberOfQuestions;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public Lesson() {
        super();
    }

    public Lesson(String title, Integer orderNumber, LessonType lessonType, Integer estimatedTime, Integer passRate, Integer timeLimitInMinutes, Integer numberOfQuestions) {
        this.title = title;
        this.orderNumber = orderNumber;
        this.lessonType = lessonType;
        this.estimatedTime = estimatedTime;
        this.passRate = passRate;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.numberOfQuestions = numberOfQuestions;
    }

    public Lesson(String title, Integer orderNumber, LessonType lessonType, Integer estimatedTime, Integer passRate, Integer timeLimitInMinutes, List<Question> questions, Chapter chapter) {
        this.title = title;
        this.orderNumber = orderNumber;
        this.lessonType = lessonType;
        this.estimatedTime = estimatedTime;
        this.passRate = passRate;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.questions = questions;
        this.chapter = chapter;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getPassRate() {
        return passRate;
    }

    public void setPassRate(Integer passRate) {
        this.passRate = passRate;
    }

    public Integer getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    public void setTimeLimitInMinutes(Integer timeLimitInMinutes) {
        this.timeLimitInMinutes = timeLimitInMinutes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
