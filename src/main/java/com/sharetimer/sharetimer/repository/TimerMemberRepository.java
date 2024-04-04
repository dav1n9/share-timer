package com.sharetimer.sharetimer.repository;

import com.sharetimer.sharetimer.domain.TimerMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerMemberRepository extends JpaRepository<TimerMember, Long> {
}
