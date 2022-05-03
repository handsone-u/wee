package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Alcohol;
import com.whatweeat.wwe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {
    Alcohol findByMenu(Menu menu);
}
