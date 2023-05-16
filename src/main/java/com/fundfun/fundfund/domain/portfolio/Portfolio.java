package com.fundfun.fundfund.domain.portfolio;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
public class Portfolio extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "portfolio_id")
    private UUID id;

    @OneToOne
    private Vote vote;

    @OneToOne
    private Users user;

    @OneToOne
    private Post post;

    private String title;
    private String ContentPortfolio;
    private String warnLevel;
    private int beneRatio;
}
