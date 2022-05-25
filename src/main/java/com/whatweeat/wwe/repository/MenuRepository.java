package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
