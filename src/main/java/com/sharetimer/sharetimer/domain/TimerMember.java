package com.sharetimer.sharetimer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimerMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMER_MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIMER_ID")
    private Timer timer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void setTimer(Timer timer) {   // 연관관계 편의 메소드
        this.timer = timer;
        System.out.println(timer.getStartTime());
        timer.getTimerMembers().add(this);
    }

    public void setMember(Member member) {   // 연관관계 편의 메소드
        this.member = member;
        member.getTimerMembers().add(this);
    }
}
