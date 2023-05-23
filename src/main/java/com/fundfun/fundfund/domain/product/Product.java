package com.fundfun.fundfund.domain.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Base64;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private UUID id;

    private String title;

    private String crowdStart;

    private String crowdEnd;

    @ColumnDefault("0")
    private Long goal;

    @ColumnDefault("0")
    private Long currentGoal;

    @ColumnDefault("'진행중'")// or 진행완료
    private String status;

    private String description;

    @ColumnDefault("'product/avatar.jpg'")
    private String thumbnailRelPath;

//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Orders orders;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users fundManager;

   /*@PrePersist
    public void prePersist() {
        this.thumbnailRelPath = this.thumbnailRelPath == null ? "/product/avatar.jpg" : this.thumbnailRelPath;
    }*/

}