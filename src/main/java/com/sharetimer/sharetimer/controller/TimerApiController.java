package com.sharetimer.sharetimer.controller;

import com.sharetimer.sharetimer.domain.Timer;
import com.sharetimer.sharetimer.dto.AddTimerRequest;
import com.sharetimer.sharetimer.dto.GetTimerResponse;
import com.sharetimer.sharetimer.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TimerApiController {
    private final TimerService timerService;

    @PostMapping("/timer/new")
    public ResponseEntity<Timer> createTimer(@RequestBody AddTimerRequest request) {
        Timer timer = timerService.createTimer(request.getTimerName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timer);
    }

    @GetMapping("/timer/{timerName}")
    public ResponseEntity<GetTimerResponse> getTimer(@PathVariable String timerName) {
        Timer timer = timerService.getTimer(timerName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetTimerResponse(timer));
    }
}
