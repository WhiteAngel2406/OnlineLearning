package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Order;
import com.swp391.OnlineLearning.model.Voucher;

import java.util.Optional;

public interface VoucherService {
    Optional<Voucher> findByCode(String code);
    double applyVoucherToAmount(Voucher voucher, double amount);
    void markUsed(Voucher voucher);
    double calculateFinalAmount(Order order, String voucherCode);
}
