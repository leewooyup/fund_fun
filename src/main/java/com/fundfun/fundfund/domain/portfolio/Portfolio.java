package com.fundfun.fundfund.domain.portfolio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.StVote;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "portfolio")
public class Portfolio extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "portfolio_id")
    private UUID id;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="vote_id")
    private Vote vote;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="post_id")
    private Post post;


    private String title;
    private String ContentPortfolio;
    private String warnLevel;
    private float beneRatio;
    
    @Builder.Default
    private StPortfolio statusPortfolio = StPortfolio.CANDIDATE;
    // 선출 여부 나타내기 위한 필드 추가

    public void linkVote(Vote vote) {this.vote = vote;}

    public void linkUsers(Users user) {this.user = user;}

    public void linkPost(Post post) {this.post = post;}

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContentPortfolio(String ContentPortfolio) {
        this.ContentPortfolio = ContentPortfolio;
    }
    public void setWarnLevel(String warnLevel){this.warnLevel = warnLevel;}
    public void setBeneRatio(float beneRatio){this.beneRatio = beneRatio;}

    public void setVote(Vote vote){this.vote = vote;}
    public void setPost(Post post){this.post = post;}
    public void setUser(Users user){this.user = user;}

    public void setStatus(StPortfolio status) {this.statusPortfolio = status;}

    //선출된 포트폴리오의 status 변경
    public void updateStatus() {
        this.statusPortfolio = StPortfolio.WINNER;
    }
}