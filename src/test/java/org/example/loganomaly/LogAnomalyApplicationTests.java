package org.example.loganomaly;

import org.example.loganomaly.model.Level;
import org.example.loganomaly.model.LogEntity;
import org.example.loganomaly.repository.Repository;
import org.example.loganomaly.service.LogGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class LogAnomalyApplicationTests {

    @MockitoBean
    private Repository repository;

    @Autowired
    private LogGeneratorService service;

    @Test
    void generateGoodLogs() throws InterruptedException {
        service.generateGoodLogs();
        verify(repository, atLeastOnce()).save(any(LogEntity.class));
    }

    @Test
    void generateGoodLogs_shouldHaveInfoLevel() throws InterruptedException {
        ArgumentCaptor<LogEntity> captor = ArgumentCaptor.forClass(LogEntity.class);

        service.generateGoodLogs();

        verify(repository, atLeastOnce()).save(captor.capture());

        LogEntity saved = captor.getValue();
        assertEquals(Level.INFO, saved.getLevel());
    }

    @Test
    void generateBadLogs_shouldSaveLog() {
        service.generateBadLogs();

        verify(repository, atLeastOnce()).save(any(LogEntity.class));
    }

    @Test
    void ddos_shouldGenerateMultipleLogs() {
        service.generateTrafficSpike();

        verify(repository,timeout(5000).atLeast(10)).save(any(LogEntity.class));
    }

    @Test
    void generateGoodLogs_shouldSetResponseTime() throws InterruptedException {
        ArgumentCaptor<LogEntity> captor = ArgumentCaptor.forClass(LogEntity.class);

        service.generateGoodLogs();

        verify(repository, atLeastOnce()).save(captor.capture());

        LogEntity log = captor.getValue();

        assertTrue(log.getResponseTime() >= 0);
    }
}
