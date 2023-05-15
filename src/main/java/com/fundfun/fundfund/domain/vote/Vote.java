package com.fundfun.fundfund.domain.vote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    private UUID voteId;
    private UUID postId;
    private String voteStart;
    private String voteEnd;
    private String status;
}
