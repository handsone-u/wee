package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Flavor;
import com.whatweeat.wwe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
    List<Flavor> findByMenu(Menu menu);
}
