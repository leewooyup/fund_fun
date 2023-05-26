package com.fundfun.fundfund.domain.payment;

import com.fundfun.fundfund.domain.payment.PayMean;
import lombok.Getter;

@Getter
public class ChargeForm {
    public Long amount;
    public PayMean mean;
}
