package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    @Autowired
    private final VoteRepository voteRepository;

    @Override
    public Vote createVote(Vote vote) {
        // Vote 생성 로직
        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> selectAll(){
        return voteRepository.findAll();
    }

    @Override
    public Vote selectVoteById(UUID voteId) {
        // Vote 조회 로직
        return voteRepository.findById(voteId).orElse(null);
    }

    @Override
    public Vote selectVoteByPostId(UUID postId) {
        Vote vote = voteRepository.findByPostId(postId);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");
        return vote;

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

    @Override
    public void deleteVote(UUID voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");

        voteRepository.delete(vote);
    }
}