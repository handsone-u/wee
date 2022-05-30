package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MenuCreateDTO;
import com.whatweeat.wwe.entity.*;
import com.whatweeat.wwe.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    public Menu save(MenuCreateDTO dto) {
        Menu newMenu = new Menu(dto.getMenuName(), dto.getMenuImage());
        MiniGameV0 newMiniGameV0 = new MiniGameV0(newMenu, dto.getRice(), dto.getNoodle(), dto.getSoup(),
                dto.getHealthy(), dto.getInstant(), dto.getAlcohol(), dto.getExpenseName());

        Set<Flavor> flavors = newMiniGameV0.getFlavors();
        Set<ExcludeFilter> excludeFilters = newMiniGameV0.getExcludeFilters();
        Set<Nation> nations = newMiniGameV0.getNations();

        dto.getFlavorNames().forEach(name ->
                flavors.add(new Flavor(newMiniGameV0, name)));
        dto.getExcludeNames().forEach(name ->
                excludeFilters.add(new ExcludeFilter(newMiniGameV0, name)));
        dto.getNationNames().forEach(name ->
                nations.add(new Nation(newMiniGameV0, name)));

        return menuRepository.save(newMenu);
    }

    @Override
    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
