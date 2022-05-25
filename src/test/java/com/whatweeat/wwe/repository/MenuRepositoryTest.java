package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.*;
import com.whatweeat.wwe.entity.enums.ExcludeName;
import com.whatweeat.wwe.entity.enums.ExpenseName;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MenuRepositoryTest {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired MenuRepository menuRepository;
    @Autowired MiniGameV0Repository miniGameV0Repository;
    @Autowired FlavorRepository flavorRepository;
    @Autowired ExcludeFilterRepository excludeFilterRepository;
    @Autowired NationRepository nationRepository;

    @Test @DisplayName(value = "CASCADE.persist 동작 확인")
    @Transactional
    void save() {
        // GIVEN
        Menu menu1 = new Menu("Menu1");

        MiniGameV0 game1 = new MiniGameV0(menu1, true, true, true, true, true, true, ExpenseName.EXPENSIVE1);
        menu1.setMiniGameV0(game1);

        Set<Flavor> flavors = game1.getFlavors();
        Set<ExcludeFilter> excludeFilters = game1.getExcludeFilters();
        Set<Nation> nations = game1.getNations();

        flavors.add(new Flavor(game1, FlavorName.BLAND));
        flavors.add(new Flavor(game1, FlavorName.COOL));
        excludeFilters.add(new ExcludeFilter(game1, ExcludeName.GUT));
        nations.add(new Nation(game1, NationName.KOR));

        // WHEN
        Menu save = menuRepository.save(menu1);

        // THEN
        assertThat(save.getId()).isNotNull();
        assertThat(save.getMenuName()).isEqualTo(menu1.getMenuName());
        assertThat(save.getMiniGameV0().getId()).isNotNull();

        assertThat(miniGameV0Repository.count()).isEqualTo(1);
        assertThat(flavorRepository.count()).isEqualTo(2);
        assertThat(excludeFilterRepository.count()).isEqualTo(1);
        assertThat(nationRepository.count()).isEqualTo(1);
    }
}