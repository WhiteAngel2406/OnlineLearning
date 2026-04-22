package com.swp391.OnlineLearning.model.dto;

import com.swp391.OnlineLearning.model.Course;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class UpdateCourseDTO {
    @NotBlank(message = "Vui lÃ²ng nháº­p tÃªn khÃ³a há»c.")
    @Size(min = 5, max = 100, message = "TÃªn khÃ³a há»c pháº£i tá»« 5 Ä‘áº¿n 100 kÃ½ tá»±.")
    private String name;

    @NotBlank(message = "Vui lÃ²ng nháº­p mÃ´ táº£ ngáº¯n.")
    @Size(min = 10, max = 200, message = "MÃ´ táº£ ngáº¯n pháº£i tá»« 10 Ä‘áº¿n 200 kÃ½ tá»±.")
    private String shortDescription;

    @NotBlank(message = "Vui lÃ²ng nháº­p mÃ´ táº£ chi tiáº¿t.")
    @Size(min = 10, max = 1000, message = "MÃ´ táº£ chi tiáº¿t pháº£i tá»« 10 Ä‘áº¿n 1000 kÃ½ tá»±.")
    private String description;

    @NotBlank(message = "Vui lÃ²ng nháº­p yÃªu cáº§u Ä‘áº§u vÃ o (prerequisite).")
    @Size(min = 10, max = 10000, message = "YÃªu cáº§u Ä‘áº§u vÃ o pháº£i tá»« 10 Ä‘áº¿n 10000 kÃ½ tá»±.")
    private String prerequisite;

    @NotNull(message = "Vui lÃ²ng nháº­p giÃ¡ khÃ³a há»c.")
    @DecimalMin(value = "0.0", message = "GiÃ¡ khÃ³a há»c pháº£i lá»›n hÆ¡n hoáº·c báº±ng 0.")
    private Double price;

    @NotNull(message = "Vui lÃ²ng nháº­p má»©c giáº£m giÃ¡.")
    @DecimalMin(value = "0.0", message = "Má»©c giáº£m giÃ¡ khÃ´ng Ä‘Æ°á»£c Ã¢m.")
    @DecimalMax(value = "100.0", message = "Má»©c giáº£m giÃ¡ khÃ´ng Ä‘Æ°á»£c vÆ°á»£t quÃ¡ 100%.")
    private Double discount;

    @NotNull(message = "Vui lÃ²ng chá»n danh má»¥c khÃ³a há»c.")
    private Long categoryId;

    private MultipartFile thumbnailFile;

    private String currentThumbnailUrl;

    private Course.CourseStatus status;

    public UpdateCourseDTO() {
        super();
    }

    public UpdateCourseDTO(Course course) {
        this.name = course.getName();
        this.shortDescription = course.getShortDescription();
        this.description = course.getDescription();
        this.prerequisite = course.getPrerequisite();
        this.price = course.getPrice();
        this.discount = course.getDiscount();
        this.categoryId = course.getCategory().getId();
        this.thumbnailFile = null;
        this.currentThumbnailUrl = course.getThumbnail();
        this.status = course.getStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public MultipartFile getThumbnailFile() {
        return thumbnailFile;
    }

    public void setThumbnailFile(MultipartFile thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public String getCurrentThumbnailUrl() {
        return currentThumbnailUrl;
    }

    public void setCurrentThumbnailUrl(String currentThumbnailUrl) {
        this.currentThumbnailUrl = currentThumbnailUrl;
    }

    public Course.CourseStatus getStatus() {
        return status;
    }

    public void setStatus(Course.CourseStatus status) {
        this.status = status;
    }
}
