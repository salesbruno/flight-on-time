package com.flightontime.repository;

import com.flightontime.model.DelayStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DelayStatsRepository extends JpaRepository<DelayStats, Long> {
    List<DelayStats> findByDataPartidaBetween(LocalDateTime inicio, LocalDateTime fim);
}
