package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.StVote;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.repository.portfolio.PortfolioRepository;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import com.fundfun.fundfund.service.opinion.OpinionService;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    @Autowired
    private final VoteRepository voteRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PortfolioRepository portfolioRepository;

    @Autowired
    private final PortfolioService portfolioService;

    @Autowired
    private final OpinionService opinionService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public Vote createVote(VoteDto voteDto) {
        // Vote 생성 로직
        Vote vote = modelMapper.map(voteDto, Vote.class);
        return voteRepository.save(vote);
    }

    @Override
    public List<VoteDto> selectAll(){
        List<Vote> voteList = voteRepository.findAll();
        List<VoteDto> voteDtoList = new ArrayList<>();
        for(Vote v : voteList){
            VoteDto voteDto = modelMapper.map(v, VoteDto.class);
            voteDto.setPostId(v.getPost().getId());
            voteDtoList.add(voteDto);
        }
        return voteDtoList;
    }

    @Override
    public VoteDto selectVoteById(UUID voteId) {
        // Vote 조회 로직
        return modelMapper.map(voteRepository.findById(voteId).orElse(null), VoteDto.class);
    }

    @Override
    public VoteDto selectVoteByPostId(UUID postId) {
        Vote vote = voteRepository.findByPostId(postId);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        return voteDto;

    }


    /*
    투표 상태 체크 및 업데이트
     */
    public boolean updateVoteStatus(VoteDto voteDto) {
        Vote vote = modelMapper.map(voteDto, Vote.class);
        Post post = postRepository.findById(voteDto.getPostId()).orElse(null);
        if(post != null){
            vote.linkPost(post);
        }
        // 현재 시간과 voteEnd 비교
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime voteEnd = vote.getVoteEnd();
        if (currentTime.isAfter(voteEnd) || currentTime.isEqual(voteEnd)) {
            if(vote.getStatus() == StVote.PROCEED) {
                vote.updateStatus();
    
                // 가장 표를 많이 받은 포트폴리오 선정
                List<PortfolioDto> portfolioList = portfolioService.selectPortByVoteId(vote.getId());
                if(portfolioList.size()>0) {
                    int max = 0;
                    PortfolioDto winner = new PortfolioDto();
                    for (PortfolioDto p : portfolioList) {
                        int count = opinionService.countByVotedFor(p);
                        if (count > max) {
                            winner = p;
                        }
                        System.out.println(p.getId() + " 포트폴리오가 받은 표수 : " + count);
                        System.out.println(p.getUserId() + ", " + p.getPostId());
                    } // 승자 선정

                    Portfolio portfolio = modelMapper.map(winner, Portfolio.class);
                    //제목, 내용 매핑
                    portfolio.updateStatus();
                    //상태 변경

                    portfolio.setVote(vote);
                    portfolio.setPost(vote.getPost());
                    Portfolio pf = portfolioRepository.findById(portfolio.getId()).orElse(null);
                    if (pf != null) {
                        Users user = pf.getUser();
                        portfolio.setUser(user);
                    }
                    //연관관계 3개 설정

                    portfolioRepository.save(portfolio);
                    // 포트폴리오의 상태 업데이트
                }
            }
        }
        voteRepository.save(vote);
        return true;
        }

    @Override
    public void deleteVote(UUID voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");

        voteRepository.delete(vote);
    }
}