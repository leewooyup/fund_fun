package com.fundfun.fundfund.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String title;

    private String startDate;

    private String endDate;

    private int goal;

    private String description;
}
