package com.fundfun.fundfund.domain.order;


import com.fundfun.fundfund.domain.payment.Payment;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orders_id")
    private UUID id;

    @ColumnDefault("0")
    private Long cost;

    @ColumnDefault("'주문완료'")//or 주문취소
    private String status;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

//    @OneToOne
//    @JoinColumn(name = "payment_id")
//    private Payment payment;
//

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

}