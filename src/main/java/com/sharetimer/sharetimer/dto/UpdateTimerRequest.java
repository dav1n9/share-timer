package com.sharetimer.sharetimer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTimerRequest {
    private String timerName;

    private String remainingTime;

    private String status;

    public UpdateTimerRequest(TimerMessageDTO message) {
        timerName = message.getTimerName();
        remainingTime = message.getRemainingTime();
        status = message.getStatus();
    }
}