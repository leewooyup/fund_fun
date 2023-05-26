package com.fundfun.fundfund.dto.vote;
import com.fundfun.fundfund.domain.vote.StVote;
import lombok.Data;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VoteDto {
    private UUID id;
    private UUID postId;
    private LocalDateTime voteStart;
    private LocalDateTime voteEnd;
    private StVote status;

}

