package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.ExcludeFilter;
import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.enums.ExcludeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExcludeFilterRepository extends JpaRepository<ExcludeFilter, Long> {
    List<ExcludeFilter> findByMenu(Menu menu);

    List<ExcludeFilter> findByExcludeNameIn(List<ExcludeName> excludeName);
}
