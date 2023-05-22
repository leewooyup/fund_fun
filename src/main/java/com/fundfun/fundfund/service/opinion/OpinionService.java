package com.fundfun.fundfund.service.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.opinion.OpinionDto;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.vote.VoteDto;

import java.util.List;
import java.util.UUID;

public interface OpinionService {
    //전체 표 조회
    List<OpinionDto> selectAll();

    //voteId 투표에 참여한 모든 표 조회
    List<OpinionDto> selectByVoteId(VoteDto voteDto);

    //portfolioId의 득표수 조회
    int countByVotedFor(PortfolioDto portfolioDto);

    //voteId, userId로 등록된 opinion 조회 - 중복 확인을 위함(존재하면 true, 존재하지 않으면 false-투표 가능)
    boolean checkOpinion(VoteDto voteDto, Users user);

    //표 등록
    void createOpinion(OpinionDto opinionDto);

    //표 수정
    void update(OpinionDto opinionDto);

    //표 삭제
    void delete(UUID opinionId);
}
