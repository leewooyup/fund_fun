package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.portfolio.StPortfolio;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.repository.portfolio.PortfolioRepository;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.user.UserRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    @Autowired
    private final PortfolioRepository portRep;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final VoteRepository voteRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public void createPort(PortfolioDto portfolioDto){
        Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
        Post post = postRepository.findById(portfolioDto.getPostId()).orElse(null);
        Vote vote = voteRepository.findById(portfolioDto.getVoteId()).orElse(null);
        Users user = userRepository.findById(portfolioDto.getUserId()).orElse(null);
        portfolio.setPost(post);
        portfolio.setVote(vote);
        portfolio.setUser(user);
        portfolio.setStatus(StPortfolio.CANDIDATE);
        portRep.save(portfolio);
    }

    //전체 포폴조회
    public List<PortfolioDto> selectAll() {
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return portRep.findAll().stream()
                .map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class)).collect(Collectors.toList());
    }

    public List<PortfolioDto> selectPortByPostId(UUID postId) {
        List<Portfolio> portfolioList = portRep.findByPostId(postId);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return portfolioList.stream()
                .map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class)).collect(Collectors.toList());
    }

    public PortfolioDto selectById(UUID portfolioId) {
        Portfolio portfolio = portRep.findById(portfolioId).orElse(null);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PortfolioDto resultDto = new PortfolioDto();
        if(portfolio != null) {
            resultDto = modelMapper.map(portfolio, PortfolioDto.class);
            resultDto.setUserId(portfolio.getUser().getId());
        }

        return resultDto;
    }


    //포트폴리오 아이디로 포폴조회
    public PortfolioDto selectPortById(UUID portfolioId){
        Portfolio portfolio = portRep.findById(portfolioId).orElse(null);
        return modelMapper.map(portfolio, PortfolioDto.class);
    };


    //보트 아이디로 포폴조회
    public List<PortfolioDto> selectPortByVoteId(UUID voteId) {
        return portRep.findByVoteId(voteId).stream().map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class)).collect(Collectors.toList());

    }


    //유저 id으로 포폴조회
    public List<PortfolioDto> selectPortByUserId(UUID userId) {
        return portRep.findByUserId(userId).stream().map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class)).collect(Collectors.toList());

    }

    //포트폴리오 삭제
    public void deletePort(PortfolioDto portfolioDto) {
        Portfolio portfolio = portRep.findById(portfolioDto.getId()).orElse(null);
        if (portfolio == null) {
            System.out.println("삭제할 포트폴리오가 존재하지 않습니다.");
            return;
        }

        // 포트폴리오 삭제 로직 추가
        portRep.delete(portfolio);

        // 삭제 후 포트폴리오가 존재하는지 확인
        Portfolio deletedPortfolio = portRep.findById(portfolioDto.getId()).orElse(null);
        if (deletedPortfolio == null) {
            System.out.println("포트폴리오가 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("포트폴리오 삭제에 실패하였습니다.");
        }
    }


    //포트폴리오 수정
    public void updatePort(PortfolioDto portfolioDto){
        Portfolio existingPort = modelMapper.map(portfolioDto, Portfolio.class);
        if (existingPort != null) {
            Post post = postRepository.findById(portfolioDto.getPostId()).orElse(null);
            Vote vote = voteRepository.findById(portfolioDto.getVoteId()).orElse(null);
            Users user = userRepository.findById(portfolioDto.getUserId()).orElse(null);
            // 변경할 필드값을 업데이트합니다.
            existingPort.setTitle(portfolioDto.getTitle());
            existingPort.setContentPortfolio(portfolioDto.getContentPortfolio());
            existingPort.setWarnLevel(portfolioDto.getWarnLevel());
            existingPort.setBeneRatio(portfolioDto.getBeneRatio());
            existingPort.setPost(post);
            existingPort.setVote(vote);
            existingPort.setUser(user);
            existingPort.setStatus(portfolioDto.getStatusPortfolio());

            // 게시물을 저장하여 업데이트합니다.
            portRep.save(existingPort);

        }else{
            throw new RuntimeException("검색한 게시물이 존재하지 않습니다.");
        }
    }


}
