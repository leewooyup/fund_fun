package com.fundfun.fundfund.service.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.opinion.OpinionDto;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.repository.opinion.OpinionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService{
    @Autowired
    private final OpinionRepository opinionRep;

    @Autowired
    private final ModelMapper modelMapper;

    //전체 표 조회
    @Override
    public List<OpinionDto> selectAll() {
        return opinionRep.findAll().stream()
                .map(opinion -> modelMapper.map(opinion, OpinionDto.class))
                .collect(Collectors.toList());
    }

    //voteId 투표에 참여한 모든 표 조회
    @Override
    public List<OpinionDto> selectByVoteId(VoteDto voteDto) {
        Vote vote = modelMapper.map(voteDto, Vote.class);
        return opinionRep.findByVote(vote).stream()
                .map(opinion -> modelMapper.map(opinion, OpinionDto.class))
                .collect(Collectors.toList());
    }

    //portfolioId의 득표수 조회
    @Override
    public int countByVotedFor(PortfolioDto portfolioDto) {
        Portfolio votedFor = modelMapper.map(portfolioDto, Portfolio.class);
        List<Opinion> list = opinionRep.findByVotedFor(votedFor);
        return list.size();
    }

    //voteId, userId로 등록된 opinion 조회 - 중복 확인을 위함(존재하면 true, 존재하지 않으면 false-투표 가능)
    @Override
    public boolean checkOpinion(Vote vote, Users user){
        Opinion o = opinionRep.findByVoteIdAndUserId(vote, user);
        if(o != null)
            return true; //해당 유저가 해당 투표에 참여한 이력이 있다면
        else
            return false; //해당 유저가 해당 투표에 참여한 이력이 없다면
    }

    @Override
    public void createOpinion(OpinionDto opinionDto){
        opinionRep.save(modelMapper.map(opinionDto, Opinion.class));
    }

    //표 수정
    @Override
    public void update(OpinionDto opinionDto) {
        Opinion op = modelMapper.map(opinionDto, Opinion.class);
        opinionRep.save(op);
    }

    //표 삭제
    @Override
    public void delete(UUID opinionId) {
        Opinion op = opinionRep.findById(opinionId).orElse(null);
        if(op == null)
            throw new RuntimeException("존재하지 않는 표입니다.");
        opinionRep.delete(op);
    }
}
