package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "course_categories")
@Getter
@Setter
public class CourseCategory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    @NotBlank(message = "TÃªn danh má»¥c khÃ³a há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(min = 5, max = 100, message = "TÃªn danh má»¥c khÃ³a há»c pháº£i cÃ³ Ä‘á»™ dÃ i tá»« 5 Ä‘áº¿n 100 kÃ½ tá»±.")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    @NotBlank(message = "MÃ´ táº£ danh má»¥c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(min = 10, max = 1000, message = "MÃ´ táº£ danh má»¥c pháº£i cÃ³ Ä‘á»™ dÃ i tá»« 10 Ä‘áº¿n 1000 kÃ½ tá»±.")
    private String description;

    @Column(nullable = false, name = "active")
    private boolean active;

    public CourseCategory() {
        active = false;
    }

    public CourseCategory(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
