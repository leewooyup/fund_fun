package com.fundfun.fundfund.domain.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opinion {
    @Id
    @GeneratedValue
    @Column(name="opinion_id")
    private UUID id;

    @JoinColumn(name="vote_id")
    private UUID voteId;

    @JoinColumn(name="user_id")
    private UUID userId;

    // portfolio id와 join 필요
    private UUID votedFor;
}
