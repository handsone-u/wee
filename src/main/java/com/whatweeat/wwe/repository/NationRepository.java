package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Long> {
}
