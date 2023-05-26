
package com.fundfun.fundfund.domain.vote;

import com.fundfun.fundfund.domain.post.Post;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="vote_id")
    private UUID id;

    @JoinColumn(name = "post_id")
    @OneToOne
    private Post post;


    private final LocalDateTime voteStart = LocalDateTime.now();
    private final LocalDateTime voteEnd = voteStart.plusMinutes(2);

    @Builder.Default
    private StVote status = StVote.PROCEED;

    //@OneToMany(mappedBy="vote")
    //private List<Opinion> opinions = new ArrayList<>();

    //@OneToMany(mappedBy="vote")
    //private List<Portfolio> portfolios = new ArrayList<>();

    public void updateStatus() {
            this.status = StVote.END;
    }

    public void linkPost(Post post) {this.post = post;}
}

