package com.fundfun.fundfund.domain.user;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.vote.Vote;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Product inprocess_product;
    private Vote inprocess_vote;
    private String password;
    private String name;
    private String email;
    private Role role;
    private String phone;
    private String gender;
    private LocalDateTime reg_date;
    private Long money;
    private Long count;
    private Long total_investment;
    private Long benefit;



}
