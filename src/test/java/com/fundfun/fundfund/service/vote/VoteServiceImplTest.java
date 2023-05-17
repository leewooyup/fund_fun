package com.fundfun.fundfund.service.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoteServiceImplTest {
    @Autowired
    VoteService voteService;

    @Test
    public void 투표_등록() throws Exception{
        for(int i=0; i<5; i++){
            voteService.createVote(Vote.builder()
                    .id(null)
                    .postId(null).voteStart(null)
                    .voteEnd(null).status("proceed").build());
        }
    }

}