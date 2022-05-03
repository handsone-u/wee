package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Health;
import com.whatweeat.wwe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {
    Health findByMenu(Menu menu);
}
