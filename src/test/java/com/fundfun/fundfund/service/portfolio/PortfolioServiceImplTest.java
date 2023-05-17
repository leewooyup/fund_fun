package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PortfolioServiceImplTest {
    @Autowired
    PortfolioService portfolioService;

    @Test
    public void portinsert() throws Exception {
        for (int i = 0; i < 10; i++) {
            Portfolio portfolio = Portfolio.builder()
                    .title("Title " + i)
                    .contentPost("Content " + i)
                    .categoryPost("Category " + i)
                    .likePost(i)
                    .build();

            postService.createPost(post);
        };
}