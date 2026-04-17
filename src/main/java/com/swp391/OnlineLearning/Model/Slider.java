package com.swp391.OnlineLearning.Model;

import com.swp391.OnlineLearning.Model.enums.SliderStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "sliders")
public class Slider extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "link_url", length = 500)
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SliderStatus status = SliderStatus.PENDING;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    public Slider() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
    public SliderStatus getStatus() { return status; }
    public void setStatus(SliderStatus status) { this.status = status; }
    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
