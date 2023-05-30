package com.fundfun.fundfund.dto.order;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class InvestDto {
    private UUID id;
    @NotNull(message = "투자금액을 입력해주세요.")
    private Long cost;
    private String status;
    private Product product;
    private Users user;
}
