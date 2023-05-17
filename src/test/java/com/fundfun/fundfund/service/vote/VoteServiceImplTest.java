package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoteServiceImplTest {
    @Autowired
    VoteService voteService;

    @Test
    public void 투표_등록() throws Exception{
        for(int i=0; i<5; i++){
            voteService.createVote(Vote.builder()
                    .id(null)
                    .post(null).voteStart(null)
                    .voteEnd(null).status(null).build());
        }
    }

    @Test
    public void 투표_전체조회(){
        List<Vote> list = voteService.selectAll();
        for(Vote v : list) System.out.println(v);
    }

    @Test
    public void 아이디로_투표_조회(){
        Vote v = voteService.selectVoteById(voteService.selectAll().get(0).getId());
        System.out.println(v);
    }

    @Test
    public void 상태_업데이트(){
        voteService.updateVoteStatus(voteService.selectAll().get(0).getId());
    }

    @Test
    public void 투표_삭제(){
        voteService.deleteVote(voteService.selectAll().get(0).getId());
    }
}