package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.*;
import com.whatweeat.wwe.entity.enums.ExpenseName;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.entity.mini_game_v0.V0Group;
import com.whatweeat.wwe.entity.mini_game_v0.V0Member;
import com.whatweeat.wwe.repository.mini_game_v0.V0GroupRepository;
import com.whatweeat.wwe.repository.mini_game_v0.V0MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MenuRepositoryTest {
    @Autowired MenuRepository menuRepository;
    @Autowired MiniGameV0Repository miniGameV0Repository;
    @Autowired FlavorRepository flavorRepository;
    @Autowired ExcludeFilterRepository excludeFilterRepository;
    @Autowired NationRepository nationRepository;
    @Autowired
    V0GroupRepository v0GroupRepository;
    @Autowired
    V0MemberRepository v0MemberRepository;

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
        excludeFilters.add(new ExcludeFilter(game1, FlavorName.INTESTINE));
        nations.add(new Nation(game1, NationName.KOREAN));

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
        assertThat(excludeFilters).extracting("excludeName").doesNotContain(FlavorName.MEAT, FlavorName.SEAFOOD);
        assertThat(nations).extracting("nationName").containsExactly(NationName.KOREAN);
    }
    private void saveGroup() {
        V0Group v0Group = new V0Group(1);
        List<V0Member> members = v0Group.getMembers();
        members.add(new V0Member("asd", v0Group));
        members.add(new V0Member("wqer", v0Group));
        v0GroupRepository.save(v0Group);

        assertThat(v0GroupRepository.count()).isEqualTo(1);
        assertThat(v0MemberRepository.count()).isEqualTo(2);
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
        excludeFilters.add(new ExcludeFilter(game1, FlavorName.INTESTINE));
        nations.add(new Nation(game1, NationName.KOREAN));

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
        V0Group v0Group = new V0Group(1);
        List<V0Member> members = v0Group.getMembers();
        members.add(new V0Member("asd", v0Group));
        members.add(new V0Member("wqer", v0Group));
        V0Group save = v0GroupRepository.save(v0Group);

        // DELETE
        v0GroupRepository.delete(save);

        assertThat(v0MemberRepository.count()).isEqualTo(0);
        assertThat(v0MemberRepository.count()).isEqualTo(0);
    }
}