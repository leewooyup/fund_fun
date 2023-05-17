package com.fundfun.fundfund.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDto {

    private String title;
    private String crowdStart;
    private String crowdEnd;
    private int goal;
    private String description;

}
