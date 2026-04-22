package com.swp391.OnlineLearning.model.dto;

import com.swp391.OnlineLearning.model.Course;
import com.swp391.OnlineLearning.util.ValidMediaType;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@ValidMediaType(message = "Vui lÃ²ng táº£i file há»£p lá»‡.")
public class CourseDTO {
    @NotBlank(message = "Vui lÃ²ng nháº­p tÃªn khÃ³a há»c")
    @Size(min = 5, max = 100, message = "TÃªn khÃ³a há»c pháº£i tá»« 5-100 kÃ­ tá»±")
    private String name;

    @NotBlank(message = "Vui lÃ²ng nháº­p mÃ´ táº£ ngáº¯n")
    @Size(min = 10, max = 200, message = "MÃ´ táº£ ngáº¯n pháº£i tá»« 10-200 kÃ­ tá»±")
    private String shortDescription;

    @NotBlank(message = "Vui lÃ²ng nháº­p mÃ´ táº£")
    @Size(min = 10, message = "MÃ´ táº£ chi tiáº¿t pháº£i tá»« 10 kÃ­ tá»±")
    private String description;

    @NotBlank(message = "Vui lÃ²ng nháº­p yÃªu cáº§u tiÃªn quyáº¿t")
    //@Size(min = 10, message = "Prerequisite must be between 10-10000 characters")
    private String prerequisite;

    @NotNull(message = "Vui lÃ²ng nháº­p giÃ¡")
    @DecimalMin(value = "1.0", message = "GiÃ¡ tiá»n pháº£i lÃ  sá»‘ dÆ°Æ¡ng")
    private Double price;

    @NotNull(message = "Vui lÃ²ng nháº­p giáº£m giÃ¡")
    @DecimalMin(value = "0.0", message = "Giáº£m giÃ¡ pháº£i tá»« 0-100")
    @DecimalMax(value = "100.0", message = "Giáº£m giÃ¡ pháº£i tá»« 0-100")
    private Double discount;

    @NotNull(message = "Vui lÃ²ng chá»n danh má»¥c")
    private Long categoryId;

    @NotNull(message = "Vui lÃ²ng chá»n thumbnail")
    private MultipartFile thumbnailFile;

    private Course.CourseStatus status;

    public CourseDTO() {
        super();
    }
    public CourseDTO(String name, String description, String prerequisite, Double price, Double discount, Long categoryId, MultipartFile thumbnailFile) {
        super();
        this.name = name;
        this.description = description;
        this.prerequisite = prerequisite;
        this.price = price;
        this.discount = discount;
        this.categoryId = categoryId;
        this.thumbnailFile = thumbnailFile;
        this.status = Course.CourseStatus.DRAFT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Course.CourseStatus getStatus() {
        return status;
    }

    public void setStatus(Course.CourseStatus status) {
        this.status = status;
    }
}
