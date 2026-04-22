package com.swp391.OnlineLearning.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BlogDTO {
    private Long id;
    private String authorAvatarUrl;
    private String authorName;
    private String title;
    private String shortDescription;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String thumbnail;
    private String categoryName;
    private String categorySlug;

    public BlogDTO() {
    }

    public BlogDTO(Long id, String authorAvatarUrl, String authorName, String title, String shortDescription, String content, LocalDateTime createdDate, LocalDateTime updatedDate, String thumbnail, String categoryName, String categorySlug) {
        super();
        this.id = id;
        this.authorAvatarUrl = authorAvatarUrl;
        this.authorName = authorName;
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.createdAt = createdDate;
        this.updatedAt = updatedDate;
        this.thumbnail = thumbnail;
        this.categoryName = categoryName;
        this.categorySlug = categorySlug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormattedContent() {
        if (content == null || content.isEmpty()) {
            return "";
        }
        String lowerContent = content.toLowerCase();
        if (lowerContent.contains("<p>") || lowerContent.contains("<br") || 
            lowerContent.contains("<h1") || lowerContent.contains("<h2") || 
            lowerContent.contains("<h3") || lowerContent.contains("<div") ||
            lowerContent.contains("<ul") || lowerContent.contains("<ol") ||
            lowerContent.contains("<li")) {
            return content;
        }
        
        String[] paragraphs = content.split("\\r?\\n\\r?\\n+");
        StringBuilder sb = new StringBuilder();
        for (String p : paragraphs) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                trimmed = trimmed.replaceAll("\\r?\\n", "<br/>");
                sb.append("<p>").append(trimmed).append("</p>\n");
            }
        }
        return sb.toString();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }
}
