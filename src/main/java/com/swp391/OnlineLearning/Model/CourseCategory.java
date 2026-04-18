package com.swp391.OnlineLearning.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course_categories")
public class CourseCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(1000)")
    private String description;

    private boolean active = true;

    public CourseCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
