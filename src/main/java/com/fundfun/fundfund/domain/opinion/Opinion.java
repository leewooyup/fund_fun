package com.fundfun.fundfund.domain.opinion;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name="opinion")
public class Opinion {
    @Id
    @GeneratedValue
    @Column(name="opinion_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolio votedFor;

    public void linkVote(Vote vote) {this.vote = vote;}

    public void linkUsers(Users user) {this.user = user;}

    public void linkPortfolio(Portfolio portfolio) {this.votedFor = portfolio;}

    public void setVote(Vote vote){this.vote = vote;}
    public void setPortfolio(Portfolio portfolio){this.votedFor = portfolio;}
    public void setUser(Users user){this.user = user;}
}