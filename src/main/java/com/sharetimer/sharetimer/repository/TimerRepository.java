package com.sharetimer.sharetimer.repository;

import com.sharetimer.sharetimer.domain.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimerRepository extends JpaRepository<Timer, Long> {
    public boolean existsByName(String name);
    public Optional<Timer> findByName(String name);
}
