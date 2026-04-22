package com.swp391.OnlineLearning.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FeedbackRequest {
    @NotNull
    @Min(value = 1, message = "Vui lÃ²ng Ä‘Ã¡nh giÃ¡ tá»« 1-5 sao.")
    @Max(value = 5, message = "Vui lÃ²ng Ä‘Ã¡nh giÃ¡ tá»« 1-5 sao.")
    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    @NotNull(message = "Vui lÃ²ng nháº­p ná»™i dung Ä‘Ã¡nh giÃ¡.")
    private String review;

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
}
