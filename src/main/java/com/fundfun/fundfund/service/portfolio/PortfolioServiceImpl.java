package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.repository.portfolio.PortfolioRepository;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public void createPort(PortfolioDto portfolioDto){
        Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
        portRep.save(portfolio);
    }

    //전체 포폴조회
    public List<PortfolioDto> selectAll() {
        return portRep.findAll().stream()
                .map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class)).collect(Collectors.toList());
    }


    //포트폴리오 아이디로 포폴조회
    public PortfolioDto selectPortById(UUID portfolioId){
        Portfolio portfolio = portRep.findById(portfolioId).orElse(null);

        //return modelMapper.map(portRep.findById(portfolioId).orElse(null), PortfolioDto.class));
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
    public void deletePort(UUID portfolioId){
        Portfolio portfolio = portRep.findById(portfolioId).orElse(null);

        if(portfolio == null)
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");
        portRep.delete(portfolio);
    }

    //포트폴리오 수정
    public void updatePort(PortfolioDto portfolioDto){
        //Portfolio existingPort = portRep.findById(portfolio.getId()).orElse(null);
        Portfolio existingPort = modelMapper.map(portfolioDto, Portfolio.class);
        if (existingPort != null) {
            // 변경할 필드값을 업데이트합니다.
            existingPort.setTitle(portfolioDto.getTitle());
            existingPort.setContentPortfolio(portfolioDto.getContentPortfolio());
            existingPort.setWarnLevel(portfolioDto.getWarnLevel());
            existingPort.setBeneRatio(portfolioDto.getBeneRatio());

            // 게시물을 저장하여 업데이트합니다.
            portRep.save(existingPort);

        }else{
            throw new RuntimeException("검색한 게시물이 존재하지 않습니다.");
        }
    }


}


