package com.fundfun.fundfund.domain.product;

import com.fundfun.fundfund.base.BaseTimeEntity;
import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.user.Users;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    private String title;
    private String crowdStart;
    private String crowdEnd;
    private Long goal;
    private Long currentGoal;
    private String status;
    private String description;
    private String thumbnailRelPath;

//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Orders orders;

//    @OneToMany(mappedBy = "orders")
//    @JoinColumn(name = "order_id")
//    private List<Orders> orders;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users fundManager;
    public String uuidEncode() {
        //UUID encode
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(this.id.toString().getBytes());

        return encodedString;
    }

    public String getThumbnailImgUrl() {
        if(thumbnailRelPath == null) return null;
        return "/gen/" + thumbnailRelPath;
    }


//    private UUID fundManager;
}