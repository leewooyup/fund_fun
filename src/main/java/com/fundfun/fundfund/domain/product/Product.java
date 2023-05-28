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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
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

    @ColumnDefault("'2023-05-29'") //test
    private String crowdEnd;

    @ColumnDefault("0")
    private Long goal;

    @ColumnDefault("0")
    private Long currentGoal;

    @ColumnDefault("'진행중'")// or 진행완료
    private String status;

    private String description;

    @ColumnDefault("'product/avatar.jpeg'")
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

    public String uuidEncode() {
        //UUID encode
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(this.id.toString().getBytes());

        return encodedString;
    }

    public Date toDate(String crowdEnd) {
        Date deadLine = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            deadLine = sdf.parse(crowdEnd);
        } catch (ParseException e) {
            e.printStackTrace(
            );
        }
        return deadLine;
    }

    public String getThumbnailImgUrl() {
        if(thumbnailRelPath == null) return "/gen/product/avatar.jpg";
        return "/gen/" + thumbnailRelPath;
    }

}