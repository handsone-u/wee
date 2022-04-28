package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
