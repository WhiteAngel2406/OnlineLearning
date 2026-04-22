package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import com.swp391.OnlineLearning.model.dto.FeedbackRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "feedbacks") // Hoáº·c "reviews"
@Getter
@Setter
public class Feedback extends BaseEntity{

    public enum FeedbackStatus {
        PENDING,    // Chá» duyá»‡t
        APPROVED,   // ÄÃ£ duyá»‡t
        REJECTED    // Bá»‹ tá»« chá»‘i
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1, message = "Vui lÃ²ng Ä‘Ã¡nh giÃ¡ tá»« 1-5 sao.")
    @Max(value = 5, message = "Vui lÃ²ng Ä‘Ã¡nh giÃ¡ tá»« 1-5 sao.")
    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    @NotNull(message = "Vui lÃ²ng nháº­p ná»™i dung Ä‘Ã¡nh giÃ¡.")
    private String review;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackStatus status; // Äá»ƒ kiá»ƒm duyá»‡t

    @Column(name = "helpful_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int helpfulCount = 0;

    @Column(name = "not_helpful_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int notHelpfulCount = 0;

    /*
     * optional = false: Má»™t feedback Báº®T BUá»˜C pháº£i gáº¯n vá»›i 1 enrollment.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, unique = true)
    private Enrollment enrollment;

    public Feedback() {
        this.status = FeedbackStatus.APPROVED; // Máº·c Ä‘á»‹nh lÃ  duyá»‡t
    }

    public Feedback(FeedbackRequest feedbackRequest, Enrollment enrollment) {
        this.rating = feedbackRequest.getRating();
        this.review = feedbackRequest.getReview();
        this.enrollment = enrollment;
        this.status = FeedbackStatus.APPROVED;
    }
    public Feedback(Integer rating, String review, Enrollment enrollment) {
        this.rating = rating;
        this.review = review;
        this.enrollment = enrollment;
        this.status = FeedbackStatus.APPROVED;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }

    public int getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public int getNotHelpfulCount() {
        return notHelpfulCount;
    }

    public void setNotHelpfulCount(int notHelpfulCount) {
        this.notHelpfulCount = notHelpfulCount;
    }

    // (Äá»«ng quÃªn thÃªm equals() vÃ  hashCode() dá»±a trÃªn ID!)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id != null && id.equals(feedback.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : getClass().hashCode();
    }
}
