package com.fundfun.fundfund.domain.user;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id;

//    @OneToMany(mappedBy = "users")
//    private List<Product> inprocess_product = new ArrayList<>();

//    @OneToMany(mappedBy = "users")
//    private List<Vote> inprocess_vote = new ArrayList<>();

//    @OneToMany(mappedBy = "users")
//    private List<Orders> inprocess_orders = new ArrayList<>();


    private String password;
    private String name;
    private String email;
    //private Role role;
    private String phone;
    private String gender;
    //private LocalDateTime reg_date;
    private Long money;
    private Long count;
    private Long total_investment;
    private Long benefit;



}
