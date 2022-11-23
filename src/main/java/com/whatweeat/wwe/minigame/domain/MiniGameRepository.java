package com.whatweeat.wwe.minigame.domain;

import com.whatweeat.wwe.group.domain.GroupPinNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniGameRepository extends JpaRepository<MiniGameGroup, GroupPinNumber> {
}
