package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Order findByOrderCode(String orderCode);

    @Query("SELECT SUM(o.amount) FROM Order o WHERE o.status = 'SUCCESS'")
    Double getTotalRevenue();
}
