package com.sharetimer.sharetimer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimerMessageDTO {
    private String messageType;     // ENTER , SEND
    private String timerName;
    private String remainingTime;
    private String startTime;
    private String status;
}