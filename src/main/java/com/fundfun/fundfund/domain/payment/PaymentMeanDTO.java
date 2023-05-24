package com.fundfun.fundfund.domain.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMeanDTO {
    private Long id;
    private Mean mean;
    private String number;
}
