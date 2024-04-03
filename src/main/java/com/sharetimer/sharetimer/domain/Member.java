package com.sharetimer.sharetimer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String nickname;

    private String message;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TimerMember> timerMembers = new ArrayList<>();

    @Builder
    public Member(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }
}
