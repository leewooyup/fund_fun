package com.fundfun.fundfund.service.payment;


import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.payment.Mean;
import com.fundfun.fundfund.domain.payment.PayMean;
import com.fundfun.fundfund.domain.payment.Payment;
import com.fundfun.fundfund.domain.payment.PaymentMeanDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.payment.PayMeanRepository;
import com.fundfun.fundfund.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PayMeanRepository payMeanRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long addPayMean(Mean mean, String number, int cvc, String vendor, Users user) {
        PayMean entity = PayMean.createPayMean(mean, number, cvc, vendor, user);
        payMeanRepository.save(entity);
        return entity.getId();
    }
    @Override
    public UUID addPayment(Users user, PayMean mean, Long cost) {
        Payment entity = Payment.createPayment(user, mean, cost);
        paymentRepository.save(entity);
        return entity.getId();
    }
    @Override
    public UUID deletePaymentById(UUID id) {
        paymentRepository.deleteById(id);
        return id;
    }
    @Override
    public Long deletePayMeanById(Long id) {
        payMeanRepository.deleteById(id);
        return id;
    }

    @Override
    public List<PaymentDTO> findAllPayment() {
        return paymentRepository.findAll().stream().map(x -> modelMapper.map(x, PaymentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PaymentMeanDTO> findAllPayMean() {
        return payMeanRepository.findAll().stream().map(x -> modelMapper.map(x, PaymentMeanDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> findAllPaymentByUserId(UUID user_id) {
        return paymentRepository.findAllByUserId(user_id).stream().map(x -> modelMapper.map(x, PaymentDTO.class)).collect(Collectors.toList());
    }
    @Override
    public List<PaymentMeanDTO> findAllPayMeanByUserId(UUID user_id) {
        return payMeanRepository.findAllByUserId(user_id).stream().map(x -> modelMapper.map(x, PaymentMeanDTO.class)).collect(Collectors.toList());
    }
    @Override
    public PaymentDTO findPaymentById(UUID id) {
        return paymentRepository.findById(id).map(x -> modelMapper.map(x, PaymentDTO.class))
                .orElseThrow(NoSuchElementException::new);
    }
    @Override
    public PaymentMeanDTO findPayMeanById(Long id) {
        return payMeanRepository.findById(id).map(x -> modelMapper.map(x, PaymentMeanDTO.class))
                .orElseThrow(NoSuchElementException::new);
    }
}
