package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.model.Order;
import com.swp391.OnlineLearning.model.Voucher;
import com.swp391.OnlineLearning.repository.VoucherRepository;
import com.swp391.OnlineLearning.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Optional<Voucher> findByCode(String code) {
        if (code == null || code.isBlank()) return Optional.empty();
        return voucherRepository.findByCode(code.trim());
    }

    @Override
    public double applyVoucherToAmount(Voucher voucher, double amount) {
        if (voucher == null) return amount;
        if (voucher.getDiscountType() == Voucher.DiscountType.PERCENT) {
            double discounted = amount * (1 - voucher.getDiscountValue() / 100.0);
            return Math.max(0, discounted);
        }
        return Math.max(0, amount - voucher.getDiscountValue());
    }

    @Override
    public void markUsed(Voucher voucher) {
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);
    }

    @Override
    public double calculateFinalAmount(Order order, String voucherCode) {
        double amount = order.getAmount();
        if (voucherCode == null || voucherCode.isBlank()) return amount;
        Voucher voucher = findByCode(voucherCode).orElse(null);
        if (voucher == null || !voucher.isUsable()) return amount;
        return applyVoucherToAmount(voucher, amount);
    }
}