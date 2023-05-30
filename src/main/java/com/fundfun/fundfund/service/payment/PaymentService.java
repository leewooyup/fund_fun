package com.fundfun.fundfund.service.payment;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.payment.Mean;
import com.fundfun.fundfund.domain.payment.PayMean;
import com.fundfun.fundfund.domain.payment.PaymentMeanDTO;
import com.fundfun.fundfund.domain.user.Users;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    Long addPayMean(Mean mean, String number, int cvc, String vendor, Users user);
    UUID addPayment(Users user, PayMean mean, Long cost);
    UUID deletePaymentById(UUID id);
    Long deletePayMeanById(Long id);

    List<PaymentDTO> findAllPayment();

    List<PaymentMeanDTO> findAllPayMean();
    List<PaymentDTO> findAllPaymentByUserId(UUID user_id);
    List<PaymentMeanDTO> findAllPayMeanByUserId(UUID user_id);
    PaymentDTO findPaymentById(UUID id);
    PaymentMeanDTO findPayMeanById(Long id);
}
