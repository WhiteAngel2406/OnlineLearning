package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class    Course extends BaseEntity{
    public enum CourseStatus {
        PUBLISHED("ÄÃ£ duyá»‡t"),
        DRAFT("Äang sá»­a"),
        PENDING("Äang chá» duyá»‡t");
        private final String displayName;
        CourseStatus(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(100)")
    @NotBlank(message = "TÃªn khÃ³a há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(min = 5, max = 100, message = "TÃªn khÃ³a há»c pháº£i cÃ³ Ä‘á»™ dÃ i tá»« 5 Ä‘áº¿n 100 kÃ½ tá»±.")
    private String name;

    @Column(name = "short_description", nullable = false, columnDefinition = "NVARCHAR(255)")
    @NotBlank(message = "MÃ´ táº£ ngáº¯n khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(min = 10, max = 200, message = "MÃ´ táº£ ngáº¯n pháº£i cÃ³ Ä‘á»™ dÃ i tá»« 10 Ä‘áº¿n 200 kÃ½ tá»±.")
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    @NotBlank(message = "MÃ´ táº£ chi tiáº¿t khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(min = 10, message = "MÃ´ táº£ chi tiáº¿t pháº£i cÃ³ Ä‘á»™ dÃ i tá»« 10 kÃ½ tá»±.")
    private String description;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    @NotBlank(message = "YÃªu cáº§u Ä‘áº§u vÃ o khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    private String prerequisite;

    @NotNull(message = "áº¢nh bÃ¬a khÃ³a há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    private String thumbnail;

    @NotNull(message = "GiÃ¡ khÃ³a há»c khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @DecimalMin(value = "0.0", message = "GiÃ¡ khÃ³a há»c pháº£i lÃ  sá»‘ dÆ°Æ¡ng.")
    private Double price;

    @NotNull(message = "GiÃ¡ trá»‹ giáº£m giÃ¡ khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @DecimalMin(value = "0.0", message = "Giáº£m giÃ¡ khÃ´ng Ä‘Æ°á»£c Ã¢m.")
    @DecimalMax(value = "100.0", message = "Giáº£m giÃ¡ khÃ´ng Ä‘Æ°á»£c vÆ°á»£t quÃ¡ 100%.")
    private Double discount;

    private boolean featured;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CourseCategory category;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    // Constructors - ÄÃƒ ÄÆ¯á»¢C Tá»I Æ¯U
    public Course() {
        this.status = CourseStatus.DRAFT;
        this.featured = false;
        this.price = 0.0;
        this.discount = 0.0;
    }

    public Course(String name, String description, String prerequisite,
                  String thumbnail, double price, double discount,
                  boolean featured, CourseStatus status) {
        this(); // Gá»i constructor máº·c Ä‘á»‹nh
        this.name = name;
        this.description = description;
        this.prerequisite = prerequisite;
        this.thumbnail = thumbnail;
        this.price = price;
        this.discount = discount;
        this.featured = featured;
        this.status = status != null ? status : CourseStatus.DRAFT;
    }

    public Course(String name, String shortDescription, String description, String prerequisite, String thumbnail, Double price, Double discount, boolean featured, CourseStatus status, int totalLesson, CourseCategory category) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.prerequisite = prerequisite;
        this.thumbnail = thumbnail;
        this.price = price;
        this.discount = discount;
        this.featured = featured;
        this.status = status;
        this.category = category;
    }

    // ThÃªm constructor tiá»‡n Ã­ch
    public Course(String name, String description, double price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }


}
