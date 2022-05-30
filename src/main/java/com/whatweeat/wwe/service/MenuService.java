package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MenuCreateDTO;
import com.whatweeat.wwe.entity.Menu;

public interface MenuService {
    Menu save(MenuCreateDTO dto);

    Menu findById(Long id);
}
