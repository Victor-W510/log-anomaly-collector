package org.example.loganomaly.schedules;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Schedule {


    @Scheduled(fixedRate = 5000)
    public void savingGoodLog(){
        log.info("savingGoodLog");
    }


}
