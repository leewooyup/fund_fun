package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.vote.VoteService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class PortfolioServiceImplTest {
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    PostService postService;
    @Autowired
    VoteService voteService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void 포폴생성() throws Exception {
        List<PostDto> list = postService.selectAll();
        PostDto postDto = list.get(1);
        Post post = modelMapper.map(postDto, Post.class);
        VoteDto voteDto = voteService.selectVoteByPostId(post.getId());
        Vote vote = modelMapper.map(voteDto, Vote.class);

        PortfolioDto portfolioDto = new PortfolioDto();
        portfolioDto.setId(UUID.randomUUID());
        portfolioDto.setVoteId(vote.getId());
        portfolioDto.setUserId(null);
        portfolioDto.setPostId(post.getId());
        portfolioDto.setTitle("제목");
        portfolioDto.setContentPortfolio("내용..");
        portfolioDto.setWarnLevel("d");
        portfolioDto.setBeneRatio(2.3F);

        portfolioService.createPort(portfolioDto);
    }

    @Test
    public void 포폴전체조회() throws Exception {
        List<PortfolioDto> list = portfolioService.selectAll();
        for (PortfolioDto port : list) System.out.println(port);
    }

    @Test
    public void 포폴아이디로조회() throws Exception {
        List<PortfolioDto> list = portfolioService.selectAll();
        UUID uuid = list.get(0).getId();
        PortfolioDto portfolioDto = portfolioService.selectById(uuid);
        Portfolio p = modelMapper.map(portfolioDto, Portfolio.class);
        System.out.println(p.getId() + " , " + p.getContentPortfolio());
    }

    @Test
    public void 보트아이디조회() throws Exception {
        List<PortfolioDto> list = portfolioService.selectAll();
        UUID uuid = list.get(10).getId();
        List<PortfolioDto> p = portfolioService.selectPortByVoteId(uuid);

        for (PortfolioDto pd : p) {
            System.out.println(pd.getId());
        }
    }//

    @Test
    public void 포트폴리오_삭제() throws Exception {
        List<PortfolioDto> list = portfolioService.selectAll();
        PortfolioDto portfolioDto = list.get(0);
        portfolioService.deletePort(portfolioDto);
    }

//    @Test
//    public void 유저아이디조회 ()throws Exception {
//        Users user = new Users();
//        List<PortfolioDto> list = portfolioService.selectAll();
//        UUID uuid = list.get(6).getId();
//        Portfolio p = portfolioService.selectPortByUserId(uuid);
//        System.out.println(p);
//    }//

}