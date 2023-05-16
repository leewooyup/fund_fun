package com.fundfun.fundfund.service.opinion;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.repository.opinion.OpinionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpinionServiceImplTest {
    @Autowired
    OpinionRepository opinionRep;

    @Autowired
    OpinionService opinionService;

    @Test
    public void 표_등록() throws Exception {
        for(int i=0; i<10; i++)
            opinionService.insert(Opinion.builder()
                    .id(null)
                    .userId(null)
                    .voteId(null)
                    .userId(null)
                    .build());
    }

    @Test
    public void 표_조회() throws Exception {
        List<Opinion> list = opinionService.selectAll();
        for(Opinion o : list) System.out.println(o);
    }

    @Test
    public void voteID에_투표한_모든표_조회() throws Exception {
        List<Opinion> list = opinionService.selectByVoteId(null);
        for(Opinion o : list) System.out.println(o);
    }

    @Test
    public void 득표수_조회() throws Exception{
        int count = opinionService.countByVotedFor(null);
        System.out.println("득표 수 = " + count);
    }

    @Test
    public void 표_삭제() throws Exception {
        Opinion op = Opinion.builder().id(null).userId(null).voteId(null).votedFor(null).build();

        UUID uuid = opinionService.selectAll().get(0).getId();
        //System.out.println(opinionService.selectAll().get(0).getId());
        opinionService.delete(uuid);

    }
}