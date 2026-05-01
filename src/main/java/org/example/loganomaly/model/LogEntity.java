package org.example.loganomaly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "logs")
@AllArgsConstructor
public class LogEntity {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private String level;
    private Integer pid;
    private String thread;
    private String logger;

    @Column(length = 500)
    private String message;
    private long responseTime;

    public LogEntity() {

    }
}