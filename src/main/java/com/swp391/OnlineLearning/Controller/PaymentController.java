package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.config.VNPayConfig;
import com.swp391.OnlineLearning.service.*;
import com.swp391.OnlineLearning.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    private final VNPayService vnPayService;
    private final OrderService orderService; // Service Ä‘á»ƒ lÆ°u Order
    private final UserService userService; // Äá»ƒ láº¥y thÃ´ng tin user
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final UserLessonService userLessonService;
    private final EmailService emailService;

    public PaymentController(VNPayService vnPayService, OrderService orderService, UserService userService, CourseService courseService, EnrollmentService enrollmentService, UserLessonService userLessonService, EmailService emailService) {
        this.vnPayService = vnPayService;
        this.orderService = orderService;
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.userLessonService = userLessonService;
        this.emailService = emailService;
    }

    @GetMapping("/payment/checkout/{courseId}")
    public String showCheckoutPage(@PathVariable("courseId") Long courseId,
                                   HttpSession session, Model model) {

        // 1. Kiá»ƒm tra Ä‘Äƒng nháº­p
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            return "redirect:/login";
        }

        // 2. Láº¥y thÃ´ng tin khÃ³a há»c
        Course course = courseService.findById(courseId);
        if (course == null) {
            return "redirect:/courses?error=NotFound"; // Vá» trang chá»§ náº¿u ko tháº¥y khÃ³a há»c
        }

        model.addAttribute("course", course);

        // 3. TÃ­nh toÃ¡n láº¡i giÃ¡ tiá»n Ä‘á»ƒ hiá»ƒn thá»‹ (an toÃ n)
        double finalPrice = course.getPrice() * (1 - course.getDiscount() / 100.0);
        model.addAttribute("finalPrice", finalPrice);

        // 4. Tráº£ vá» file HTML checkout má»›i
        return "user/checkout";
    }

    // Endpoint nÃ y Ä‘Æ°á»£c gá»i khi ngÆ°á»i dÃ¹ng nháº¥n nÃºt "Thanh toÃ¡n"
    @PostMapping("/payment/create-vnpay-order")
    public String createVNPayOrder(@RequestParam("courseId") Long courseId,
                                   HttpServletRequest request,
                                   HttpSession session) throws UnsupportedEncodingException {

        // 1. Láº¥y thÃ´ng tin User (vÃ­ dá»¥ tá»« session)
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            return "redirect:/login"; // ChÆ°a Ä‘Äƒng nháº­p
        }
        User currentUser = this.userService.getUserById(userId);
        Course currentCourse = this.courseService.findById(courseId);
        // 2. Táº¡o Ä‘á»‘i tÆ°á»£ng Order vÃ  lÆ°u vÃ o DB vá»›i status PENDING
        Order newOrder = this.orderService.createNewOrder(currentUser, currentCourse);

        String paymentUrl = vnPayService.createVNPayPaymentUrl(newOrder, request);

        // 3. Chuyá»ƒn hÆ°á»›ng ngÆ°á»i dÃ¹ng sang VNPay
        return "redirect:" + paymentUrl;
    }

    //Code chuáº©n: khi deploy, sáº½ xÃ¡c minh vÃ  cáº­p nháº­t db á»Ÿ Ä‘Ã¢y chá»© khÃ´ng pháº£i /payment/vnpay-ipn
    /*
    @GetMapping("/payment/vnpay-ipn")
    @ResponseBody
    public String handleIPN(HttpServletRequest request) {
        // 1. Láº¥y táº¥t cáº£ tham sá»‘ VNPAY gá»­i vá»
        Map<String, String> vnpParams = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String fieldValue = request.getParameter(fieldName);
            vnpParams.put(fieldName, fieldValue);
        }

        // 2. Gá»i Service Ä‘á»ƒ xá»­ lÃ½
        String response = this.vnPayService.processIPN(vnpParams);

        // 3. náº¿u response thÃ nh cÃ´ng, update vÃ o enrollment.

        // 3. Tráº£ vá» chuá»—i JSON "00" hoáº·c "97", "01",... cho VNPAY
        return response;
    }*/

    @GetMapping("/payment/vnpay-return")
    public String handleVNPayReturn(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //checkSum
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        String hashData = VNPayConfig.buildHashData(fields);

        String signValue = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData);

        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                Order updatedOrder = this.orderService.update(fields);
                Enrollment newEnrollment = this.enrollmentService.createNew(updatedOrder);
                this.userLessonService.createFullUserLesson(newEnrollment);
                emailService.sendPurchasedNotification(
                        newEnrollment.getUser().getEmail(),
                        newEnrollment
                );
                redirectAttributes.addAttribute("status", "success");
            } else {
                redirectAttributes.addAttribute("status", "failed");
            }
        } else {
            redirectAttributes.addAttribute("status", "invalid signature");
        }
        return "redirect:/payment/result";
    }

    @GetMapping("/payment/result")
    public String showPaymentResult(@RequestParam Map<String, String> params, Model model) {
        // Láº¥y cÃ¡c tham sá»‘ tá»« URL (do redirectAttributes thÃªm vÃ o)
        model.addAttribute("params", params);

        // (Báº¡n cáº§n táº¡o file HTML cho trang nÃ y)
        return "user/paymentResult";
    }
}
