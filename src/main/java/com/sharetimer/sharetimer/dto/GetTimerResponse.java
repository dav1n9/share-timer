package com.sharetimer.sharetimer.dto;

import com.sharetimer.sharetimer.domain.Timer;
import lombok.Getter;

@Getter
public class GetTimerResponse {
    private final String timerName;

    private final String remainingTime;

    private final String startTime;

    private final String status;

    public GetTimerResponse(Timer timer) {
        timerName = timer.getName();
        remainingTime = timer.getRemainingTime();
        startTime = String.valueOf(timer.getStartTime());
        status = String.valueOf(timer.getStatus());
    }

}
