package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.vote.VoteDto;

import java.util.List;
import java.util.UUID;

public interface VoteService {

    //투표 생성
    Vote createVote(VoteDto voteDto);

    //투표 전체 조회
    List<VoteDto> selectAll();

    //투표 아이디로 조회
    VoteDto selectVoteById(UUID voteId);

    //게시물아이디로 투표 조회
    VoteDto selectVoteByPostId(UUID postId);

    //voteId에 해당하는 투표의 상태 업데이트
    void updateVoteStatus(VoteDto voteDto);
    
    //투표 삭제
    void deleteVote(UUID voteId);
}
