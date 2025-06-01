package com.yunsseong.barrier_free_map_server.member.repository;

import com.yunsseong.barrier_free_map_server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
