package com.fundfun.fundfund.domain.product;

import com.fundfun.fundfund.domain.order.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "product_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String crowdStart;
    private String crowdEnd;
    private int goal;
    private int currentGoal;
    private String status;
    private String description;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    private UUID userId;
}