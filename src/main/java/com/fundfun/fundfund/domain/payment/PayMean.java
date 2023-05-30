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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Mean mean;
    // 카드 번호 또는 계좌 번호
    private String mean_id;
    private int cvc;
    private String vendor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


    public static PayMean createPayMean(Mean mean, String number, int cvc, String vendor, Users user) {
        PayMean payMean = new PayMean(null, mean, number, cvc, vendor, user);
        user.addPayMean(payMean);
        return payMean;
    }

}
