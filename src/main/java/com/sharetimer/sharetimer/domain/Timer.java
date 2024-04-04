package com.sharetimer.sharetimer.domain;

import com.sharetimer.sharetimer.constant.TimerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMER_ID")
    private Long id;

    @Column(unique = true)
    private String name;

    private String remainingTime;

    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    private TimerStatus status;

    @OneToMany(mappedBy = "timer", cascade = CascadeType.ALL)
    private List<TimerMember> timerMembers = new ArrayList<>();

    @Builder
    public Timer(String remainingTime, String name, LocalDateTime startTime, TimerStatus status) {
        this.remainingTime = remainingTime;
        this.name = name;
        this.startTime = startTime;
        this.status = status;
    }
}
