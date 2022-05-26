package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.MiniGameGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MiniGameGroupRepository extends JpaRepository<MiniGameGroup, Integer> {
    @Query("SELECT MAX(id) FROM MiniGameGroup")
    int getMaxId();
}
