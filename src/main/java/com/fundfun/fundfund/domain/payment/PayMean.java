package com.fundfun.fundfund.domain.payment;

import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayMean extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Mean mean;
    private String number;

    @ManyToOne
    private Users user;

    public static PayMean createPayMean(Mean mean, String number, Users user) {
        PayMean payMean = new PayMean(null, mean, number, user);
        user.addPayMean(payMean);
        return payMean;
    }

}
