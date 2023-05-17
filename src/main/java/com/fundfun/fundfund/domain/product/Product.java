package com.fundfun.fundfund.domain.product;

import com.fundfun.fundfund.base.BaseTimeEntity;
import com.fundfun.fundfund.domain.order.Orders;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private UUID id;

    private String crowdStart;
    private String crowdEnd;
    private Integer goal;
    private Integer currentGoal;
    private String status;
    private String description;

//    @OneToMany(mappedBy = "orders")
//    @JoinColumn(name = "order_id")
//    private List<Orders> orders;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users users;
}