package com.fundfun.fundfund.domain.vote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue
    private UUID voteId;
    private UUID postId;
    private LocalDateTime voteStart;
    private LocalDateTime voteEnd;
    private String status;

    public void updateStatus() {
        if (LocalDateTime.now().isAfter(voteEnd)) {
            this.status = "end";
        }
    }
}
