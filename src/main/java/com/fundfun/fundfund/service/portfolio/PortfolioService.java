package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;


import java.util.List;
import java.util.UUID;

public interface PortfolioService {

    //포트폴리오 생성
    void createPort(PortfolioDto portDto);

    //전체 포트폴리오 조회
    List<PortfolioDto> selectAll();

    //포폴id로 포트폴리오 조회

    PortfolioDto selectById(UUID portfolioId);

    //보트 id로 포폴조회
    List<PortfolioDto> selectPortByVoteId(UUID voteId);

    List<PortfolioDto> selectPortByPostId(UUID postId);

    //유저 id로 포트폴리오 조회
    List<PortfolioDto> selectPortByUserId(UUID UserId);


    //포트폴리오 삭제

    void deletePort(PortfolioDto portfolioDto);


    //포트폴리오 수정
    void updatePort(PortfolioDto portfolioDto);

}