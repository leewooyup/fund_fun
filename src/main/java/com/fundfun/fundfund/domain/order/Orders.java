package com.fundfun.fundfund.domain.order;

import com.fundfun.fundfund.base.BaseTimeEntity;
import com.fundfun.fundfund.domain.payment.Payment;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import lombok.*;
import lombok.experimental.SuperBuilder;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orders_id")
    private UUID id;

    private Long cost;

//    @ColumnDefault("F")
    private String status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @OneToOne
//    @JoinColumn(name = "payment_id")
//    private Payment payment;
//

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public void linkProduct(Product product){
        this.product = product;
    }

    public void linkUser(Users users){
        this.user = users;
    }

    public void setCost(Long cost){
        this.cost = cost;
    }

}