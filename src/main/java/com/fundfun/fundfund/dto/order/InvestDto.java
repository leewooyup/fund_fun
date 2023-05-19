package com.fundfun.fundfund.dto.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InvestDto {
    @NotNull(message = "투자금액을 입력해주세요.")
    private Long cost;
}
