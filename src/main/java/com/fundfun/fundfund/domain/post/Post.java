package com.fundfun.fundfund.domain.post;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private UUID id;
    private String title;
    private String contentPost;
    @Builder.Default
    private int likePost = 0;
    @Builder.Default
    private String categoryPost = "주식형";
    @OneToOne
    private Vote vote;

    //@ColumnDefault("'EARLY_IDEA'")
    @Builder.Default
    private StPost statusPost = StPost.EARLY_IDEA;

    @OneToMany(mappedBy = "post")
    private List<Portfolio> portfolios = new ArrayList<>();

    public void setStatusPost(StPost statusPost) {
        this.statusPost = statusPost;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public void setLikePost(int likePost){ this.likePost = likePost; }

    public void linkVote(Vote vote){
        this.vote = vote;
    }
}
