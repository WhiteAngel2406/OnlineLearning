package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Order findByOrderCode(String orderCode);
}
