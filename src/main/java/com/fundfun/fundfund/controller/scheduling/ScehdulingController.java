package com.fundfun.fundfund.controller.scheduling;

import com.fundfun.fundfund.base.Scheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ScehdulingController {
    private static final Logger log = LoggerFactory.getLogger(Scheduling.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Scheduled(fixedRate = 2000)
    public void reportCurTime() {
        String format = dateFormat.format(new Date());
        System.out.println("The time is now " + format);
    }
}
