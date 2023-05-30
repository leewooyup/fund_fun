//package com.fundfun.fundfund.service.opinion;
//
//import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
//import com.fundfun.fundfund.domain.opinion.Opinion;
//import com.fundfun.fundfund.domain.portfolio.Portfolio;
//import com.fundfun.fundfund.domain.user.Users;
//import com.fundfun.fundfund.domain.vote.Vote;
//import com.fundfun.fundfund.repository.opinion.OpinionRepository;
//import com.fundfun.fundfund.repository.post.PostRepository;
//import com.fundfun.fundfund.service.portfolio.PortfolioService;
//import com.fundfun.fundfund.service.user.UserService;
//import com.fundfun.fundfund.service.vote.VoteService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class OpinionServiceImplTest {
//    @Autowired
//    OpinionRepository opinionRep;
//
//    @Autowired
//    OpinionService opinionService;
//
//    @Autowired
//    VoteService voteService;
//
//    @Autowired
//    PortfolioService portfolioService;
//
//    @Autowired
//    PostRepository postRep;
//
//    //@Autowired
//    //UserService userService;
//
//
//
//    @Test
//    public void 표_등록() throws Exception {
////        for(int i=0; i<10; i++)
////            opinionService.insert(Opinion.builder()
////                    .id(null)
////                    .user(null)
////                    .vote(null)
////                    .votedFor(null)
////                    .build());
//    }
//
//    @Test
//    public void 표_조회() throws Exception {
//        List<Opinion> list = opinionService.selectAll();
//        for(Opinion o : list) System.out.println(o);
//    }
//
//    @Test
//    public void voteID에_투표한_모든표_조회() throws Exception {
//        List<Opinion> list = opinionService.selectByVoteId(null);
//        for(Opinion o : list) System.out.println(o);
//    }
//
//    @Test
//    public void 득표수_조회() throws Exception{
//        int count = opinionService.countByVotedFor(null);
//        System.out.println("득표 수 = " + count);
//    }
//
//    @Test
//    public void 표_삭제() throws Exception {
//        Opinion op = Opinion.builder().id(null).user(null).vote(null).votedFor(null).build();
//
//        UUID uuid = opinionService.selectAll().get(0).getId();
//        //System.out.println(opinionService.selectAll().get(0).getId());
//        opinionService.delete(uuid);
//
//    }
//
//    @Test
//    public void 투표_지정_후_표_등록() throws Exception{
////        Users user = new Users(); // 유저 데이터 넣은 후 다시 테스트
//        List<Portfolio> pList = portfolioService.selectAll();
//        Portfolio portfolio = pList.get(1);
//
//        List<Vote> vList = voteService.selectAll();
//        Vote vote = vList.get(0);
//
//        for(int i=0; i<4; i++)
//            opinionService.insertOpinion(null, vote, portfolio);
//
//        List<Opinion> oList = opinionService.selectAll();
//        //for(Opinion o : oList) System.out.println(/*user.getId() + */" 님이 " + portfolio.getId() + " 포트폴리오에 투표했습니다.");
//    }
//
//    @Test
//    public void 표수_조회(){
//        List<Vote> vList = voteService.selectAll();
//        //Vote vote = vList.get(5);
//        System.out.println();
//        List<Portfolio> pList = portfolioService.selectAll();
//
//        //Portfolio portfolio = portfolioService.selectPortByVoteId(vote.getId());
//        Portfolio portfolio = pList.get(0);
//        int count = opinionService.countByVotedFor(portfolio);
//        System.out.println(portfolio.getVote().getId() + " 투표에 올라온 " + portfolio.getId() + " 포트폴리오에 투표한 count = " + count);
//    }
//}