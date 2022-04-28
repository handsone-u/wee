package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MenuDto;
import com.whatweeat.wwe.dto.MenuPoint;
import com.whatweeat.wwe.entity.*;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor @Slf4j
@Service
@Transactional(readOnly = true)
public class MenuSearchService {
    private final MenuRepository menuRepository;

    // Count 1,-1, exclude
    public List<MenuPoint> searchModel1(MenuDto menuDto) {
        log.info(menuDto.toString());
        List<Menu> exclude = menuRepository.findExceptExcludeNames(menuDto.getExcludeNames());
        ArrayList<MenuPoint> result = new ArrayList<>();

        for (Menu menu : exclude) {
            int nationCount = matchNationNames(menu.getNations(), menuDto.getNationNames());
            int flavorCount = matchFlavors(menu.getFlavors(), menuDto.getFlavorNames());
            int styleCount = matchStyle(menu.getStyle(), menuDto.getIsSoup(), menuDto.getIsNoodle(), menuDto.getIsRice());
            int healthCount = matchHealth(menu.getHealth(), menuDto.getIsHealthy());
            int instantCount = matchInstant(menu.getInstant(), menuDto.getIsInstant());
            int alcoholCount = matchAlcohol(menu.getAlcohol(), menuDto.getIsAlcohol());
            int richCount = matchRich(menu.getRich(), menuDto.getIsRich());
            log.info("[{}]:{},{},{},{},{},{},{}", menu.getId(),
                    nationCount, flavorCount, styleCount, healthCount, instantCount, alcoholCount, richCount);
            int point = nationCount + flavorCount + styleCount + healthCount + instantCount + alcoholCount + richCount;
            result.add(new MenuPoint(menu.getId(), menu.getMenuName(), point));
        }

        return result;
    }

    public int matchNationNames(List<Nation> nations, List<NationName> nationNames) {
        int result = 0;
        for (NationName name : nations.stream()
                .map(Nation::getNationName)
                .collect(Collectors.toList())) {
            if(nationNames.contains(name)) result++;
        }
        return result;
    }

    public int matchFlavors(List<Flavor> flavors, List<FlavorName> flavorNames) {
        int result = 0;
        for (FlavorName name : flavors.stream()
                .map(Flavor::getFlavorName)
                .collect(Collectors.toList())) {
            if(flavorNames.contains(name)) result++;
        }
        return result;
    }

    public int matchStyle(Style style, Boolean isSoup, Boolean isNoodle, Boolean isRice) {
        if(style==null) return 0;
        int result = 0;
        if(style.getIsSoup()==isSoup) result++;
        else result--;
        if(style.getIsNoodle()==isNoodle) result++;
        else result--;
        if(style.getIsRice()==isRice) result++;
        else result--;
        return result;
    }

    public int matchHealth(Health health, Boolean isHealthy) {
        if(health==null) return 0;
        if(health.getIsHealthy()==isHealthy) return 1;
        else return -1;
    }

    public int matchInstant(Instant instant, Boolean isInstant) {
        if(instant==null) return 0;
        if(instant.getIsInstant()==isInstant) return 1;
        else return -1;
    }

    public int matchAlcohol(Alcohol alcohol, Boolean isAlcohol) {
        if(alcohol == null) return 0;
        if(alcohol.getIsAlcohol() == isAlcohol) return 1;
        else return -1;
    }

    public int matchRich(Rich rich, Boolean isRich) {
        if(rich==null) return 0;
        if(rich.getIsRich()==isRich) return 1;
        else return -1;
    }
}