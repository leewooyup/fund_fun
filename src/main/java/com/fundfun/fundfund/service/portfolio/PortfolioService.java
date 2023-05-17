package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioService {

    //전체 포트폴리오 조회
    List<Portfolio> selectAll();

    //제목으로 포트폴리오 조회
    List<Portfolio> selectPortfolioByKeyword(String keyword);

    //작성자로 포트폴리오 조회
    Optional<Portfolio> selectPortfolioByUserId(UUID userId);
    
    //위험도로 포트폴리오 조회
    List<Portfolio> selectPortfolioByWarnLevel(String warnLevel);
    
    //예상수익율로 포트폴리오 조회
    List<Portfolio> selectPortfolioByBeneRatio(int beneRatio);


    //포트폴리오 삭제
    void delete(Portfolio portfolio);

    //포트폴리오 수정
    void updatePort(Portfolio portfolio);

}
