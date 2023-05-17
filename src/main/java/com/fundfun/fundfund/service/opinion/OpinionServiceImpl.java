package com.fundfun.fundfund.service.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.repository.opinion.OpinionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService{
    @Autowired
    private final OpinionRepository opinionRep;

    //전체 표 조회
    @Override
    public List<Opinion> selectAll() {
        return opinionRep.findAll();
    }

    //voteId 투표에 참여한 모든 표 조회
    @Override
    public List<Opinion> selectByVoteId(UUID voteId) {
        return opinionRep.findByVoteId(voteId);
    }

    //portfolioId의 득표수 조회
    @Override
    public int countByVotedFor(UUID votedFor) {
        List<Opinion> list = opinionRep.findByVotedFor(votedFor);
        return list.size();
    }

    //표 등록
    @Override
    public void insertOpinion(Users user, Vote vote, Portfolio portfolio) {
        Opinion op = new Opinion();
        op.linkUsers(user);
        op.linkVote(vote);
        op.linkPortfolio(portfolio);

        opinionRep.save(op);
    }

    //표 수정
    @Override
    public void update(Opinion opinion) {
        Opinion op = opinionRep.findById(opinion.getId()).orElse(null);
        if (op == null)
            throw new RuntimeException("표 번호 오류로 수정할 수 없습니다.");

        opinionRep.delete(op);
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
