package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MenuCreateDTO;
import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.enums.FlavorName;

import java.util.List;
import java.util.Set;

public interface MenuService {
    Long count();

    Menu save(MenuCreateDTO dto);

    Menu findById(Long id);

    void deleteAll();

    Menu findByMenuName(String menuName);

    Menu findOneRandom();

    List<Menu> findAll();

    List<Menu> findAllExceptFlavorNames(Set<FlavorName> flavorNames);
}
