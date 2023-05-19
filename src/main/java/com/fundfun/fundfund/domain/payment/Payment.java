package com.fundfun.fundfund.domain.payment;

import com.fundfun.fundfund.domain.order.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private UUID id;

//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Orders orders;
//    private String paidDate;
}