package com.fundfun.fundfund.domain.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Product {
    @Id
    private int productId;

    private String crowdStart;
    private String crowdEnd;
    private int goal;
    private int currentGoal;
    private String status;
    private String description;
}
