package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PortfolioServiceImplTest {
    @Autowired
    PortfolioService portfolioService;

    @Test
    public void portinsert() throws Exception {
        for (int i = 0; i < 10; i++) {
            Portfolio port = Portfolio.builder()
                    .title("title" + i)
                    .ContentPortfolio(null)
                    .warnLevel(null)
                    .beneRatio(i)
                    .build();
            portfolioService.createPort(port);
        }
        ;
    }
    @Test
    public void 포폴전체조회() throws Exception {
        List<Portfolio> list = portfolioService.selectAll();
        for(Portfolio port : list) System.out.println(port);
    }

    @Test
    public void 제목_포폴조회() throws Exception {
        List<Portfolio> list = portfolioService.selectPortfolioByKeyword(null);
        for(Portfolio port : list) System.out.println(port);
    }

    @Test
    public void 작성자_포폴조회() throws Exception {
        Optional<Portfolio> olist = portfolioService.selectPortfolioByUserId(null);
        olist.ifPresent(portfolio -> System.out.println(portfolio));

    }

    @Test
    public void 위험도_포폴조회() throws Exception {
        List<Portfolio> list = portfolioService.selectPortfolioByWarnLevel(null);
        for(Portfolio port : list) System.out.println(port);
    }

    @Test
    public void 예상수익률_포폴조회() throws Exception {
        List<Portfolio> list = portfolioService.selectPortfolioByBeneRatio(null);
        for(Portfolio port : list) System.out.println(port);
    }
}