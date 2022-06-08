package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MenuCreateDTO;
import com.whatweeat.wwe.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu save(MenuCreateDTO dto);

    Menu findById(Long id);

    List<Menu> findAll();
}
