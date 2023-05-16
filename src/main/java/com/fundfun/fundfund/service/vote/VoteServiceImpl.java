package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.repository.vote.VoteRepository;

import java.util.UUID;

public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote createVote(Vote vote) {
        // Vote 생성 로직
        return voteRepository.save(vote);
    }

    @Override
    public Vote getVoteById(UUID voteId) {
        // Vote 조회 로직
        return voteRepository.findById(voteId).orElse(null);
    }

    @Override
    public void updateVoteStatus(UUID voteId) {
        // Vote 상태 업데이트 로직
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if (vote != null) {
            vote.updateStatus();
            voteRepository.save(vote);
        }
    }


}
