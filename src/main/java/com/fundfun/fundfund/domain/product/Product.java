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
    @Column(name = "product_id")
    private int productId;
    @Column(name = "crowd_start")
    private String crowdStart;
    @Column(name = "crowd_end")
    private String crowdEnd;
    private int goal;
    @Column(name = "current_goal")
    private int currentGoal;
    private String status;
    private String description;
}
