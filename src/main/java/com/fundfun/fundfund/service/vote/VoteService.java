package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.repository.vote.VoteRepository;

import java.util.UUID;

public interface VoteService {

    Vote createVote(Vote vote);
    Vote getVoteById(UUID voteId);
    void updateVoteStatus(UUID voteId);

}
