package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Page<Order> findByCourseId(Long courseId, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    long countByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Order o WHERE o.status = 'SUCCESS'")
    Double getTotalRevenue();

    @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Order o WHERE o.status = 'SUCCESS' AND o.orderDate BETWEEN :start AND :end")
    Double getRevenueByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT o FROM Order o WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR o.orderCode LIKE %:keyword%) AND " +
           "(:status IS NULL OR o.status = :status) AND " +
           "(:startDate IS NULL OR o.orderDate >= :startDate) AND " +
           "(:endDate IS NULL OR o.orderDate <= :endDate)")
    Page<Order> findWithFilters(@Param("keyword") String keyword,
                                @Param("status") OrderStatus status,
                                @Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate,
                                Pageable pageable);
}
