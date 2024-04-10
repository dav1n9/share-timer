package com.sharetimer.sharetimer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddTimerRequest {
    private String timerName;
}