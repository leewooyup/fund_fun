package com.fundfun.fundfund.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Weight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "weight_id_gen")
    @SequenceGenerator(name = "weight_id_gen", allocationSize = 1, sequenceName = "weight_id_gen")
    private Integer weightId;

    private Integer weight;

    private String productTitle;
}
