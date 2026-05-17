package org.example.loganomaly.schedules;


import lombok.extern.slf4j.Slf4j;
import org.example.loganomaly.service.LogGeneratorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Schedule {


    private final LogGeneratorService logGeneratorService;

    public Schedule(LogGeneratorService logGeneratorService) {
        this.logGeneratorService = logGeneratorService;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void savingGoodLog() throws InterruptedException {

        logGeneratorService.generateGoodLogs();
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void savingRandom() {

        logGeneratorService.generateBadLogs();
    }

    @Scheduled(cron = "0 30 * * * ?")
    public void attack(){
        if (Math.random() < 0.2) {
            logGeneratorService.generateTrafficSpike();
        }
    }


}
