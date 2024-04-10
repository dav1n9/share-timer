package com.sharetimer.sharetimer.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharetimer.sharetimer.dto.TimerMessageDTO;
import com.sharetimer.sharetimer.dto.UpdateTimerRequest;
import com.sharetimer.sharetimer.service.TimerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<String, Set<WebSocketSession>> timerSessions = new HashMap<>();
    private final TimerService timerService;

    // 연결을 맺고나서 실행되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        log.info("session: {}", session);

        sessions.add(session);
    }

    // 메시지를 다루는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        TimerMessageDTO timerMessageDTO = mapper.readValue(payload, TimerMessageDTO.class);
        log.info(timerMessageDTO.toString());

        String timerName = timerMessageDTO.getTimerName();

        timerSessions.computeIfAbsent(timerName, key -> new HashSet<>()).add(session);

        // 해당 타이머의 세션 그룹 가져오기
        Set<WebSocketSession> sessionsInChat = timerSessions.get(timerName);
        for (WebSocketSession s : sessionsInChat) {
            try {
                s.sendMessage(message);
            } catch (IOException e) {
                log.error("Failed to send message to session {}", s.getId(), e);
            }
        }

        timerService.updateTimer(timerName, new UpdateTimerRequest(timerMessageDTO));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        timerSessions.values().forEach(sessions -> sessions.remove(session));
    }
}