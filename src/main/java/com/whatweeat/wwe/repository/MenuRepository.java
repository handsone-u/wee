package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.repository.qdsl.MenuQDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuQDslRepository {
    List<Menu> findByMenuName(String name);
}
