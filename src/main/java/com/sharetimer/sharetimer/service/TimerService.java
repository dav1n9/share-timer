package com.sharetimer.sharetimer.service;

import com.sharetimer.sharetimer.constant.TimerStatus;
import com.sharetimer.sharetimer.domain.Timer;
import com.sharetimer.sharetimer.dto.UpdateTimerRequest;
import com.sharetimer.sharetimer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimerService {
    private final TimerRepository timerRepository;

    public Timer createTimer(String timerName) {
        Timer timer;
        if (timerRepository.findByName(timerName).isPresent()) {   // 타이머가 이미 존재하면 멤버 추가
            throw new IllegalArgumentException("이미 존재하는 타이머 이름입니다.");
        } else {
            timer = Timer.builder()
                    .name(timerName)
                    .remainingTime("00:00:00")
                    .startTime(null)
                    .status(TimerStatus.STOP)
                    .build();
            timerRepository.save(timer);
        }
        return timer;
    }

    public Timer getTimer(String timerName) {
        Timer timer = timerRepository.findByName(timerName)
                .orElseThrow(() -> new IllegalArgumentException("not found timer name : " + timerName));

        // 만약 start 상태이면 타이머의 남은 시간 계산 필요함.
        if (timer.getStatus() == TimerStatus.START) {
            long remainingSeconds = timeStringToSeconds(timer.getRemainingTime()); // 원래 남은 시간을 초단위로 계산
            LocalDateTime endTime = timer.getStartTime().plusSeconds(remainingSeconds); // 타이머가 끝날 시간
            // 현재 시간과 종료 시간의 차이를 구하고 이를 "hh:mm:ss" 형식의 문자열로 변환
            String newRemainingTime = secondsToTimeString(Duration.between(LocalDateTime.now(), endTime).getSeconds());

            timer.setStartTime(LocalDateTime.now());
            timer.setRemainingTime(newRemainingTime);
        }
        return timer;
    }

    @Transactional
    public void updateTimer(String timerName, UpdateTimerRequest request) {
        Timer timer = getTimer(timerName);
        System.out.println(request.toString());

        TimerStatus status;
        if (request.getStatus() == null) status = TimerStatus.STOP;
        else status = request.getStatus().equals("START") ? TimerStatus.START : TimerStatus.STOP;
        timer.update(request.getRemainingTime(), status);

    }

    private long timeStringToSeconds(String timeString) {
        String[] parts = timeString.split(":");
        // 시, 분, 초를 각각 정수로 변환하여 초 단위로 합산
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        return hours * 3600L + minutes * 60L + seconds;
    }

    private String secondsToTimeString(long totalSeconds) {
        // 시, 분, 초 계산
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        // 문자열로 변환 후 포맷팅
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
