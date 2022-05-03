package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.Nation;
import com.whatweeat.wwe.entity.enums.NationName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NationRepository extends JpaRepository<Nation, Long> {
    List<Nation> findByMenu(Menu menu);

    List<Nation> findByNationNameIn(List<NationName> nationNames);
}
