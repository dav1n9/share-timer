package com.sharetimer.sharetimer.controller;

import com.sharetimer.sharetimer.domain.Timer;
import com.sharetimer.sharetimer.dto.AddTimerRequest;
import com.sharetimer.sharetimer.dto.GetTimerResponse;
import com.sharetimer.sharetimer.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TimerViewController {
    private final TimerService timerService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/timer/new")
    public String createTimer(AddTimerRequest request) {
        Timer timer = timerService.createTimer(request.getTimerName());
        return "redirect:/timer/" + timer.getName();
    }

    @GetMapping("/timer/{timerName}")
    public String getTimer(@PathVariable String timerName, Model model) {
        Timer timer = timerService.getTimer(timerName);
        model.addAttribute("timer", new GetTimerResponse(timer));
        return "timer";
    }
}