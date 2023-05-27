package com.fundfun.fundfund.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "items_id_gen")
    @SequenceGenerator(name = "items_id_gen", allocationSize = 1, sequenceName = "items_id_gen")
    private Integer itemId;

    @Column(name = "item_name")
    private String itemsName;

    private String productTitle;
}
