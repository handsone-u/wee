package com.whatweeat.wwe.service;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.Nation;
import com.whatweeat.wwe.entity.Style;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MenuSearchServiceTest {
    @InjectMocks
    private MenuSearchService service;
    @Mock
    private MenuRepository repository;
    List<Menu> menus = new ArrayList<>();
    List<Nation> nations = new ArrayList<>();
    List<Style> styles = new ArrayList<>();

    @BeforeEach
    void be() {
        Menu m1 = new Menu("test1");
        Menu m2 = new Menu("test2");
        Menu m3 = new Menu("test3");
        List<Menu> mList = List.of(m1, m2, m3);

        Nation n1_1 = new Nation(m1, NationName.CHN);
        Nation n1_2 = new Nation(m1, NationName.KOR);
        Nation n2 = new Nation(m2, NationName.EXO);
        Nation n3 = new Nation(m3, NationName.KOR);
        m1.getNations().add(n1_1);
        m1.getNations().add(n1_2);
        m2.getNations().add(n2);
        m3.getNations().add(n3);

        Style s1 = new Style(m1, true, true, true);
        Style s2 = new Style(m2, true, false, false);
        Style s3 = new Style(m3, false, true, true);
        m1.setStyle(s1);
        m2.setStyle(s2);
        m3.setStyle(s3);

        menus.add(m1);
        menus.add(m2);
        menus.add(m3);
        nations.add(n1_1);
        nations.add(n1_2);
        nations.add(n2);
        nations.add(n3);
        styles.add(s1);
        styles.add(s2);
        styles.add(s3);
    }

    @Test
    void matchStyle() {
        assertThat(service.matchStyle(styles.get(0), false, false, false))
                .isEqualTo(-3);
        assertThat(service.matchStyle(styles.get(0), true, false, false))
                .isEqualTo(-1);
        assertThat(service.matchStyle(styles.get(0), false, true, false))
                .isEqualTo(-1);
        assertThat(service.matchStyle(styles.get(0), false, false, true))
                .isEqualTo(-1);
        assertThat(service.matchStyle(styles.get(2), false, true, true))
                .isEqualTo(3);
    }

    @Test
    void matchNationNames() {
        assertThat(service.matchNationNames(styles.get(0).getMenu().getNations(), List.of(NationName.CHN)))
                .isEqualTo(1);
        assertThat(service.matchNationNames(styles.get(0).getMenu().getNations(), List.of(NationName.CHN, NationName.KOR)))
                .isEqualTo(2);
        assertThat(service.matchNationNames(styles.get(1).getMenu().getNations(), List.of(NationName.KOR, NationName.WEST)))
                .isEqualTo(0);
    }
}