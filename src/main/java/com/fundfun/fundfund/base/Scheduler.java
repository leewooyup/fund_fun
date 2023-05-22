package com.fundfun.fundfund.base;

import com.fundfun.fundfund.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class Scheduler {
//    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final OrderServiceImpl orderService;
//    @Scheduled(fixedRate = 1000)
//    public void reportCurTime() {
//        log.info("Java fixedRate Thread={}", Thread.currentThread().getName());
//    }

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

    @Scheduled(cron = "0/10 * * * * ?")
    public void sayHello() {
        orderService.sayHello();
    }
}
