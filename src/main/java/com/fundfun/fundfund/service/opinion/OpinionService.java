package com.fundfun.fundfund.service.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;

import java.util.List;
import java.util.UUID;

public interface OpinionService {
    //전체 표 조회
    List<Opinion> selectAll();

    //voteId 투표에 참여한 모든 표 조회
    List<Opinion> selectByVoteId(UUID voteId);

    //portfolioId의 득표수 조회
    int countByVotedFor(UUID votedFor);

    //표 등록
    void insertOpinion(Users user, Vote vote, Portfolio portfolio);

    //표 수정
    void update(Opinion opinion);

    //표 삭제
    void delete(UUID opinionId);
}
