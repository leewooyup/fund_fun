package com.fundfun.fundfund.domain.portfolio;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    private Vote vote;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Post post;

    private String title;
    private String ContentPortfolio;
    private String warnLevel;
    private int beneRatio;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContentPortfolio(String contentPortfolio) {
        this.ContentPortfolio = contentPortfolio;
    }

    public void setWarnLevel(String warnLevel){
        this.warnLevel = warnLevel;
    }

    public void setBeneRatio(int warnLevel){
        this.beneRatio = beneRatio;
    }
}
