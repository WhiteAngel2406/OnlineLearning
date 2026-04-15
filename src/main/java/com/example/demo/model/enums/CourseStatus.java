package com.example.demo.model.enums;

public enum CourseStatus {
    PENDING("Chờ duyệt"),
    APPROVED("Đã duyệt"),
    REJECTED("Từ chối");

    private final String displayName;
    CourseStatus(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
