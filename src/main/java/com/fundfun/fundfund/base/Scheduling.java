package com.fundfun.fundfund.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Scheduling {
    private static final Logger log = LoggerFactory.getLogger(Scheduling.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void reportCurTime() {
        String format = dateFormat.format(new Date());
        System.out.println("The time is now " + format);
    }
}
