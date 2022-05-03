package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.Rich;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RichRepository extends JpaRepository<Rich, Long> {
    Rich findByMenu(Menu menu);
}
