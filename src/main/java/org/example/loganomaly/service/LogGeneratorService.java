package org.example.loganomaly.service;

import lombok.extern.slf4j.Slf4j;
import org.example.loganomaly.model.Level;
import org.example.loganomaly.model.LogEntity;
import org.example.loganomaly.repository.Repository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class LogGeneratorService {


    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final Repository repository;

    public LogGeneratorService(Repository repository) {
        this.repository = repository;
    }

    public void generateGoodLogs() throws InterruptedException {

        double random = Math.random();
        String message;
        Level level = Level.INFO;
        long start = System.currentTimeMillis();
        Thread.sleep((long) (Math.random() * 100));
        long end = System.currentTimeMillis() - start;

        if (random < 0.2) {
            message = "User request processed successfully successfully";
            log.info("{}, responsTime {} ms", message, end);
        } else if (random < 0.4) {
            message = "Starting service [Tomcat]";
            log.info(message);
        }else if (random < 0.6) {
            message = "Payment_succeeded amount=249";
            log.info("{}, responsTime {} ms", message, end);
        }else if (random < 0.8) {
            message = "Payment_failed reason=card_declined card_declined";
            log.info(message);
            level = Level.WARN;
        }else{
            message = "User_login userId=44";
            log.info("{}, responsTime {} ms", message, end);
        }

        saveLog(level, message, start);

    }

    public void generateBadLogs() {

        long start = System.currentTimeMillis();
        double random = Math.random();

        if (random < 0.3) {
            generateRandomLogs(start);
        } else {
            log.error("SQL Error: 1054, SQLState: 28000");
            sleep();
            saveLog(Level.ERROR, "SQL Error: 1054, SQLState: 28000", start);

        }
    }

    public void generateTrafficSpike(){
        for (int i = 0; i < 50; i++) {
            executor.submit(() -> {
                try {
                    generateGoodLogs();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
    }

    private void generateRandomLogs(long start) {
        double random = Math.random();
        Level level;
        String message;

        if (random < 0.2) {
            message = "ORDER_CREATED orderId=55 userId=123";
            log.debug(message);
            level = Level.DEBUG;

        }else if (random < 0.5) {
            level = Level.ERROR;
            message = "SQL CHANGE orderId=55 from=CREATED to=PAID";
            log.info(message);


        }else{
            level = Level.ERROR;
            message = "SYSTEM_ANOMALY memory_usage=99% 12345678902 " +
                    "SYSTEM_ANOMALY memory_usage=99% 12345678902 " +
                    "SYSTEM_ANOMALY memory_usage=99% 12345678902 " +
                    "SYSTEM_ANOMALY memory_usage=99% 12345678902";
            log.error(message);
        }


        saveLog(level, message, start);

    }


    private void sleep() {
        long millis = (long)(Math.random() * 1000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void saveLog(Level level, String message, long start){
        repository.save(LogEntity.builder()
                .timestamp(LocalDateTime.now())
                .level(level)
                .pid((int) ProcessHandle.current().pid())
                .thread(Thread.currentThread().getName())
                .logger(this.getClass().getName())
                .message(message)
                .responseTime(System.currentTimeMillis() - start)
                .build());
    }


}
