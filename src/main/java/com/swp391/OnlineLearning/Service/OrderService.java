package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Model.Order;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.OrderFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createNewOrder(User currentUser, Course currentCourse);

    /*String processIPN(Map<String, String> vnpParams);*/

  /*  // BỔ SUNG: Hàm xử lý logic cho Return URL
    Order processReturn(Map<String, String> vnpParams);*/

    Order update(Map fields);
    List<Order> getAllOrders();
    List<Order> getOrdersWithSpecs(OrderFilter filter);
    Order getOrderById(Long id);
}
