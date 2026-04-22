package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Course;
import com.swp391.OnlineLearning.model.Order;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.dto.OrderFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createNewOrder(User currentUser, Course currentCourse);

    /*String processIPN(Map<String, String> vnpParams);*/

  /*  // Bá»” SUNG: HÃ m xá»­ lÃ½ logic cho Return URL
    Order processReturn(Map<String, String> vnpParams);*/

    Order update(Map fields);
    List<Order> getAllOrders();
    List<Order> getOrdersWithSpecs(OrderFilter filter);
    Order getOrderById(Long id);
}
