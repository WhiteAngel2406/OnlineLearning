package com.swp391.OnlineLearning.Model.dto;

import java.time.LocalDateTime;

public class EnrollmentInfoDTO {

    private Long id;
    private String courseTitle;
    private String courseThumbnailUrl;
    private String courseShortDescription;

    private LocalDateTime completedAt;
    private LocalDateTime lastAccessAt;
    private LocalDateTime enrolledAt;

    private int progress;
    private long totalLessons;
    private long completedLessons;

    public EnrollmentInfoDTO(
            Long id,
            String courseTitle,
            String courseThumbnailUrl,
            String courseShortDescription,
            LocalDateTime completedAt,
            LocalDateTime lastAccessAt,
            LocalDateTime enrolledAt,
            long totalLessons,
            long completedLessons
    ) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseThumbnailUrl = courseThumbnailUrl;
        this.courseShortDescription = courseShortDescription;
        this.completedAt = completedAt;
        this.lastAccessAt = lastAccessAt;
        this.enrolledAt = enrolledAt;
        this.totalLessons = totalLessons;
        this.completedLessons = completedLessons;

        // ✅ FIX chuẩn
        if (totalLessons > 0) {
            this.progress = (int) Math.round((completedLessons * 100.0) / totalLessons);
        } else {
            this.progress = 0;
        }
    }

    // ================= GETTER =================

    public Long getId() {
        return id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseThumbnailUrl() {
        return courseThumbnailUrl;
    }

    public String getCourseShortDescription() {
        return courseShortDescription;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getLastAccessAt() {
        return lastAccessAt;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public int getProgress() {
        return progress;
    }

    public long getTotalLessons() {
        return totalLessons;
    }

    public long getCompletedLessons() {
        return completedLessons;
    }
}