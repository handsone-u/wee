package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.MiniGameMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniGameMemberRepository extends JpaRepository<MiniGameMember, Long> {
}
