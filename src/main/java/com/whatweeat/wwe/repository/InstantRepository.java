package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Instant;
import com.whatweeat.wwe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstantRepository extends JpaRepository<Instant, Long> {
    Instant findByMenu(Menu menu);
}
