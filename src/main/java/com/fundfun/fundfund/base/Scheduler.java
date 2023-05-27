package com.fundfun.fundfund.base;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class Scheduler {
    //    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final OrderServiceImpl orderService;
    private final ProductService productService;
    private final VoteService voteService;

    @Scheduled(fixedRate = 10000)
    public void reportCurTime() {
        log.info("Java fixedRate Thread={}", Thread.currentThread().getName());
    }

//    @Scheduled(fixedDelay = 1000)
//    public void reportCurTimeThread() {
//        log.info("Java fixedDelay Thread={}", Thread.currentThread().getName());
//    }

//    @Scheduled(cron = "0/10 * * * * ?")
//    public void cronJobSch() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        log.info("Java cron job Test = {}", strDate);
//    }

    /**
     * 매일 0분 0시 0초, 펀딩 상태 갱신
     *//*
    @Scheduled(cron = "0 0 0 * * * ")
    public void sayHello() {
        List<ProductDto> productDtoList = productService.selectAll();
        for (ProductDto productDto : productDtoList) {
            productService.updateStatus(productDto);
        }
    }


    *//**
     * 매일 0분 0시 0초, 투표 상태 갱신
     */
    @Scheduled(cron = "59 * * * * * ")
    public void voteStauts() {
        List<VoteDto> voteDtoList = voteService.selectAll();
        for (VoteDto voteDto : voteDtoList) {
            voteService.updateVoteStatus(voteDto);
        }

    }


}