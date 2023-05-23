package com.fundfun.fundfund.controller.payment;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.payment.PaymentMeanDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.payment.PayMeanRepository;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.service.payment.PaymentDTO;
import com.fundfun.fundfund.service.payment.PaymentService;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ff/api/v1/payment")
public class PaymentRestController {
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ApiResponse<List<PaymentDTO>> getAllPayments() {
        return ApiResponse.success(paymentService.findAllPayment());
    }
    @GetMapping
    public ApiResponse<List<PaymentMeanDTO>> getAllPayMeans() {
        return ApiResponse.success(paymentService.findAllPayMean());
    }

    @GetMapping
    public ApiResponse<List<PaymentDTO>> getAllPaymentsByUserId(@RequestBody Map<String, Object> map) {
//        return ApiResponse.success(paymentService.findAllPaymentByUserId());
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<List<PaymentMeanDTO>> getAllPayMeansByUserId(@RequestBody Map<String, Object> map) {
//        return ApiResponse.success(paymentService.findAllPayMeanByUserId());
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<PaymentDTO> getPaymentById(@RequestBody Map<String, Object> map) {
//        return ApiResponse.success(paymentService.findPaymentById());
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<PaymentMeanDTO> getPayMeanById(@RequestBody Map<String, Object> map) {
//        return ApiResponse.success(paymentService.findPayMeanById());
        return ApiResponse.success(null);
    }

    @PostMapping
    public ApiResponse<String> addPayment(@RequestBody Map<String, Object> map) {
        Users user = userService.findById(UUID.fromString((String) map.get("user_id"))).get();
//        Orders order = orderService.
//        paymentService.addPayment((Users)map.get("user"), );
        return ApiResponse.success("success");
    }
    @PostMapping
    public ApiResponse<String> addPayMean(@AuthenticationPrincipal Users user, @RequestBody Map<String, Object> map) {
//        paymentService.addPayMean(Enum.valueOf((String)map.get("mean")),(String)map.get(), user);
        return ApiResponse.success("success");
    }
    @PostMapping
    public ApiResponse<String> cancelPaymentById(UUID uuid) {
        paymentService.deletePaymentById(uuid);
        return ApiResponse.success("success");
    }

    @PostMapping
    public ApiResponse<String> deletePayMean(Long id) {
        paymentService.deletePayMeanById(id);
        return ApiResponse.success("success");
    }
}
