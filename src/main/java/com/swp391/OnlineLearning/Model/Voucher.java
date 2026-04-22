package com.swp391.OnlineLearning.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
public class Voucher extends BaseEntity {

    public enum DiscountType {
        PERCENT,
        FIXED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType;

    @Column(nullable = false)
    private Double discountValue;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Integer maxUses = 1;

    @Column(nullable = false)
    private Integer usedCount = 0;

    private LocalDateTime expiresAt;

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean isUsable() {
        return active && !isExpired() && usedCount < maxUses;
    }
}