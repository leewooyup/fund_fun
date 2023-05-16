package com.fundfun.fundfund.domain.vote;

import com.fundfun.fundfund.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue
    @Column(name = "vote_id")
    private UUID voteId;

//    private LocalDateTime voteStart;
//    private LocalDateTime voteEnd;
    private String status;

//    public void updateStatus() {
//        if (LocalDateTime.now().isAfter(voteEnd)) {
//            this.status = "end";
//        }
//    }
}
