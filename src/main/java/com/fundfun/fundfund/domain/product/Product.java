package com.fundfun.fundfund.domain.product;

import com.fundfun.fundfund.domain.order.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private UUID fundManager;
}