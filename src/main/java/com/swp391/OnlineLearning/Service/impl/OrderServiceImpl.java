package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.config.VNPayConfig;
import com.swp391.OnlineLearning.model.Course;
import com.swp391.OnlineLearning.model.Order;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.dto.OrderFilter;
import com.swp391.OnlineLearning.repository.OrderRepository;
import com.swp391.OnlineLearning.service.OrderService;
import com.swp391.OnlineLearning.service.specification.OrderSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createNewOrder(User currentUser, Course currentCourse) {
        Order newOrder = new Order();
        newOrder.setOrderCode(VNPayConfig.getRandomNumber(8));
        newOrder.setAmount(currentCourse.getPrice() * (1 - currentCourse.getDiscount()/100));
        newOrder.setOrderInfo("THANH TOAN KHOA HOC");
        newOrder.setUser(currentUser);
        newOrder.setCourse(currentCourse);
        newOrder.setStatus(Order.OrderStatus.PENDING);
        return this.orderRepository.save(newOrder);
    }

    /**
     * HÃ m xá»­ lÃ½ IPN (Code chuáº©n)
     */
    /*@Override
    public String processIPN(Map<String, String> vnpParams) {
        final String RspCode_Success = "00";
        final String RspCode_OrderNotFound = "01";
        final String RspCode_OrderAlreadyConfirmed = "02";
        final String RspCode_InvalidAmount = "04";
        final String RspCode_InvalidSignature = "97";
        final String RspCode_SystemError = "99";
        try
        {
            String vnp_SecureHash = vnpParams.get("vnp_SecureHash");
            vnpParams.remove("vnp_SecureHashType");
            vnpParams.remove("vnp_SecureHash");

            // Check checksum
            String signValue = VNPayConfig.hashAllFields(vnpParams);
            if (!signValue.equals(vnp_SecureHash)){
                return "{\"RspCode\":\"" + RspCode_InvalidSignature + "\",\"Message\":\"Invalid Checksum\"}";
            }
            // 3. Láº¥y thÃ´ng tin
            // vnp_TxnRef chÃ­nh lÃ  Order ID cá»§a chÃºng ta
            String vnp_TxnRef = vnpParams.get("vnp_TxnRef");
            String vnp_Amount_str = vnpParams.get("vnp_Amount");
            String vnp_ResponseCode = vnpParams.get("vnp_ResponseCode");
            String vnp_TransactionNo = vnpParams.get("vnp_TransactionNo");

            // 4. Kiá»ƒm tra Order (checkOrderId)
            Order order = this.orderRepository.findById(Long.parseLong(vnp_TxnRef)).orElse(null);
            if (order == null) {
                return "{\"RspCode\":\"" + RspCode_OrderNotFound + "\",\"Message\":\"Order not Found\"}";
            }

            // 5. Kiá»ƒm tra sá»‘ tiá»n (checkAmount)
            // Sá»¬A Lá»–I 3: Logic so sÃ¡nh Amount bá»‹ sai
            long vnpAmount = Long.parseLong(vnp_Amount_str); // (Vd: 152000000)
            long orderAmount = (long) (order.getAmount() * 100); // (Vd: 1520000.0 * 100 = 152000000)
            if (orderAmount != vnpAmount) {
                return "{\"RspCode\":\"" + RspCode_InvalidAmount + "\",\"Message\":\"Invalid Amount\"}";
            }

            // 6. Kiá»ƒm tra tráº¡ng thÃ¡i (checkOrderStatus)
            if (order.getStatus() != Order.OrderStatus.PENDING) {
                return "{\"RspCode\":\"" + RspCode_OrderAlreadyConfirmed + "\",\"Message\":\"Order already confirmed\"}";
            }

            // 7. Cáº­p nháº­t tráº¡ng thÃ¡i
            if ("00".equals(vnp_ResponseCode)) {
                order.setStatus(Order.OrderStatus.PAID);
            } else {
                order.setStatus(Order.OrderStatus.FAILED);
            }

            // 8. LÆ°u báº±ng chá»©ng vÃ  Cáº¬P NHáº¬T
            order.setVnpResponseCode(vnp_ResponseCode);
            order.setVnpTransactionNo(vnp_TransactionNo);
            this.orderRepository.save(order);

            // 9. Tráº£ vá» 00
            return "{\"RspCode\":\"" + RspCode_Success + "\",\"Message\":\"Confirm Success\"}";
        }

        catch(Exception e)
        {
            return "{\"RspCode\":\"" + RspCode_SystemError + "\",\"Message\":\"System Error\"}";
        }
    }*/

/*
    @Override
    public Order processReturn(Map<String, String> vnpParams) {
        // 1. Kiá»ƒm tra chá»¯ kÃ½ (Checksum)
        String vnp_SecureHash = vnpParams.get("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");
        vnpParams.remove("vnp_SecureHash");

        String signValue = VNPayConfig.hashAllFields(vnpParams);
        if (!signValue.equals(vnp_SecureHash)) throw new RuntimeException("Invalid checksum");

        // 2. Láº¥y thÃ´ng tin
        String vnp_TxnRef = vnpParams.get("vnp_TxnRef");
        String vnp_Amount_str = vnpParams.get("vnp_Amount");
        String vnp_ResponseCode = vnpParams.get("vnp_ResponseCode");
        String vnp_TransactionNo = vnpParams.get("vnp_TransactionNo");

        // 3. Kiá»ƒm tra Order
        Order order = this.findByVnp_TxnRef(vnp_TxnRef);
        if (order == null) throw new RuntimeException("Order not found");

        // 4. Kiá»ƒm tra sá»‘ tiá»n
        long vnpAmount = Long.parseLong(vnp_Amount_str);
        long orderAmount = (long) (order.getAmount() * 100);
        if (orderAmount != vnpAmount) throw new RuntimeException("Invalid amount");

        // 5. Kiá»ƒm tra tráº¡ng thÃ¡i (Chá»‰ cáº­p nháº­t náº¿u Ä‘ang PENDING)
        if (order.getStatus() == Order.OrderStatus.PENDING) {
            if ("00".equals(vnp_ResponseCode)) {
                order.setStatus(Order.OrderStatus.PAID);
            } else {
                order.setStatus(Order.OrderStatus.FAILED);
            }
            // 6. LÆ°u báº±ng chá»©ng vÃ  Cáº¬P NHáº¬T DB
            order.setVnpResponseCode(vnp_ResponseCode);
            order.setVnpTransactionNo(vnp_TransactionNo);
            orderRepository.save(order);

        }
        // Náº¿u khÃ´ng PENDING (Ä‘Ã£ xá»­ lÃ½ bá»Ÿi IPN) thÃ¬ cá»© tráº£ vá» order
        return order;
    }
*/

    @Override
    public Order update(Map fields) {
        Order order = this.orderRepository.findByOrderCode(fields.get("vnp_TxnRef").toString());
        order.setStatus(Order.OrderStatus.PAID);
        order.setVnpResponseCode("00");
        order.setVnpTransactionNo(fields.get("vnp_TransactionNo").toString());
        return this.orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersWithSpecs(OrderFilter filter) {
//        String range = filter.;
        String status = filter.getStatus();
        String sortBy = filter.getSortBy();
        String direction = filter.getSortDir();
        String search = filter.getSearch();
        if (direction == null) direction = "DESC";
        direction = direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";
        LocalDateTime updatedFrom = filter.getStartUpdate();
        LocalDateTime updatedTo = filter.getEndUpdate();

        Specification<Order> spec = null;
        if (updatedFrom != null) {
            spec = OrderSpecs.fromUpdateDate(updatedFrom);
        }
        if (updatedTo != null) {
            spec = OrderSpecs.toUpdateDate(updatedTo);
        }
        if (search != null && !search.isEmpty()) {
            spec = OrderSpecs.hasOrderCode(search);
        }
        if(status != null && !status.isEmpty())
        spec = OrderSpecs.hasStatus(status);

        Sort sort = Sort.by(Sort.Direction.fromString(direction),  sortBy != null ? sortBy : "updatedAt");
//
//        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository.findAll(spec, sort);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
