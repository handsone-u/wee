package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.*;
import com.whatweeat.wwe.entity.enums.ExcludeName;
import com.whatweeat.wwe.entity.enums.ExpenseName;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MenuRepositoryTest {
    @Autowired MenuRepository menuRepository;
    @Autowired MiniGameV0Repository miniGameV0Repository;
    @Autowired FlavorRepository flavorRepository;
    @Autowired ExcludeFilterRepository excludeFilterRepository;
    @Autowired NationRepository nationRepository;
    @Autowired MiniGameGroupRepository miniGameGroupRepository;
    @Autowired MiniGameMemberRepository miniGameMemberRepository;

    @Test @DisplayName(value = "CASCADE.persist 동작 확인")
    @Transactional
    void save() {
        saveMenu();
        saveGroup();
    }

    @Test @DisplayName(value = "CASCADE.delete")
    @Transactional
    void delete() {
        deleteMenu();
        deleteGroup();
    }

    private void saveMenu() {
        // GIVEN
        Menu menu1 = new Menu("Menu1");
        MiniGameV0 game1 = new MiniGameV0(menu1, true, true, true, true,
                true, true, ExpenseName.EXPENSIVE1);

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

        assertThat(save.getMiniGameV0().getMenu()).isNotNull();

        assertThat(miniGameV0Repository.count()).isEqualTo(1);
        assertThat(flavorRepository.count()).isEqualTo(2);
        assertThat(excludeFilterRepository.count()).isEqualTo(1);
        assertThat(nationRepository.count()).isEqualTo(1);

        assertThat(flavors).extracting("flavorName").containsOnly(FlavorName.BLAND, FlavorName.COOL);
        assertThat(excludeFilters).extracting("excludeName").doesNotContain(ExcludeName.MEAT, ExcludeName.SEA);
        assertThat(nations).extracting("nationName").containsExactly(NationName.KOR);
    }
    private void saveGroup() {
        MiniGameGroup miniGameGroup = new MiniGameGroup(1);
        List<MiniGameMember> members = miniGameGroup.getMembers();
        members.add(new MiniGameMember("asd", miniGameGroup, new MiniGameResultV0()));
        members.add(new MiniGameMember("wqer", miniGameGroup, new MiniGameResultV0()));
        miniGameGroupRepository.save(miniGameGroup);

        assertThat(miniGameGroupRepository.count()).isEqualTo(1);
        assertThat(miniGameMemberRepository.count()).isEqualTo(2);
    }
    private void deleteMenu() {
        Menu menu1 = new Menu("Menu1");
        MiniGameV0 game1 = new MiniGameV0(menu1, true, true, true, true,
                true, true, ExpenseName.EXPENSIVE1);

        Set<Flavor> flavors = game1.getFlavors();
        Set<ExcludeFilter> excludeFilters = game1.getExcludeFilters();
        Set<Nation> nations = game1.getNations();

        flavors.add(new Flavor(game1, FlavorName.BLAND));
        flavors.add(new Flavor(game1, FlavorName.COOL));
        excludeFilters.add(new ExcludeFilter(game1, ExcludeName.GUT));
        nations.add(new Nation(game1, NationName.KOR));

        Menu save = menuRepository.save(menu1);

        // DELETE
        menuRepository.delete(save);

        assertThat(menuRepository.count()).isEqualTo(0);
        assertThat(miniGameV0Repository.count()).isEqualTo(0);
        assertThat(flavorRepository.count()).isEqualTo(0);
        assertThat(excludeFilterRepository.count()).isEqualTo(0);
        assertThat(nationRepository.count()).isEqualTo(0);
    }
    private void deleteGroup() {
        MiniGameGroup miniGameGroup = new MiniGameGroup(1);
        List<MiniGameMember> members = miniGameGroup.getMembers();
        members.add(new MiniGameMember("asd", miniGameGroup, new MiniGameResultV0()));
        members.add(new MiniGameMember("wqer", miniGameGroup, new MiniGameResultV0()));
        MiniGameGroup save = miniGameGroupRepository.save(miniGameGroup);

        // DELETE
        miniGameGroupRepository.delete(save);

        assertThat(miniGameMemberRepository.count()).isEqualTo(0);
        assertThat(miniGameMemberRepository.count()).isEqualTo(0);
    }
}