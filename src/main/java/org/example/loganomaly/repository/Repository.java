package org.example.loganomaly.repository;

import org.example.loganomaly.model.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<LogEntity, Long> {
}
