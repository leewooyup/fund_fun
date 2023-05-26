package com.fundfun.fundfund.dto.vote;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.vote.StVote;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VoteDto {
    private UUID id;
    private Post post;
    private LocalDateTime voteStart;
    private LocalDateTime voteEnd;
    private StVote status;

}

