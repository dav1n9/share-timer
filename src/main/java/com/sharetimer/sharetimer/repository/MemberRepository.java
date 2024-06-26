package com.sharetimer.sharetimer.repository;

import com.sharetimer.sharetimer.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByNickname(String nickname);
}
