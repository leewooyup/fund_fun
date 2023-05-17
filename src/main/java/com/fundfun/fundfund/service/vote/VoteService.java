package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;

import java.util.List;
import java.util.UUID;

public interface VoteService {

    //투표 생성
    public Vote createVote(Vote vote);

    //투표 전체 조회
    public List<Vote> selectAll();

    //투표 아이디로 조회
    public Vote selectVoteById(UUID voteId);

    //voteId에 해당하는 투표의 상태 업데이트
    public void updateVoteStatus(UUID voteId);
    
    //투표 삭제
    public void deleteVote(UUID voteId);
}
