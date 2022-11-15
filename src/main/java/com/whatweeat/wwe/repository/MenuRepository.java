package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;

import java.util.Optional;

public interface MenuRepository {
    Optional<Menu> findByMenuName(String menuName);
}
