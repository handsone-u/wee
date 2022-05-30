package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MiniGameResultDTO;
import com.whatweeat.wwe.entity.enums.ExcludeName;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.entity.mini_game_v0.V0Group;
import com.whatweeat.wwe.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class V0GroupServiceTest {
    @Autowired V0GroupRepository v0GroupRepository;
    @Autowired V0MemberRepository v0MemberRepository;
    @Autowired V0FlavorRepository flavorRepository;
    @Autowired V0ExcludeRepository excludeRepository;
    @Autowired V0NationRepository nationRepository;

    @Test
    @Transactional
    void idGenerator() {
        MiniGameV0ServiceImpl service = new MiniGameV0ServiceImpl(v0GroupRepository, v0MemberRepository);
        service.createGroup();
        service.createGroup();
        assertThat(v0GroupRepository.count()).isEqualTo(2);
    }

    @Test @DisplayName("그룹 생성 & 그룹 참여")
    void createGroupAndJoinGroup() {
        MiniGameV0ServiceImpl service = new MiniGameV0ServiceImpl(v0GroupRepository, v0MemberRepository);

        int pin = service.createGroup();
        System.out.println("pin = " + pin);
        assertThat(v0GroupRepository.count()).isEqualTo(1);
        assertThat(v0MemberRepository.count()).isEqualTo(0);

        MiniGameResultDTO hello = makeDTO(pin);

        V0Group save = service.saveResult(hello);
        assertThat(v0GroupRepository.count()).isEqualTo(1);
        assertThat(v0MemberRepository.count()).isEqualTo(1);
        assertThat(v0MemberRepository.findAll()).extracting("complete")
                .containsOnly(true);
        assertThat(flavorRepository.count()).isEqualTo(0);
        assertThat(excludeRepository.count()).isEqualTo(1);
        assertThat(excludeRepository.findAll()).extracting("excludeName")
                .containsExactly(ExcludeName.GUT);
        assertThat(nationRepository.count()).isEqualTo(2);
        assertThat(nationRepository.findAll()).extracting("nationName")
                .containsOnly(NationName.KOR, NationName.EXO);

        assertThat(save.getId()).isEqualTo(pin);
    }

    @Test @DisplayName("그룹 제거")
    void deleteGroup() {
        MiniGameV0ServiceImpl service = new MiniGameV0ServiceImpl(v0GroupRepository, v0MemberRepository);

        int pin = service.createGroup();
        System.out.println("pin = " + pin);
        assertThat(v0GroupRepository.count()).isEqualTo(1);
        assertThat(v0MemberRepository.count()).isEqualTo(0);

        MiniGameResultDTO hello = makeDTO(pin);
        MiniGameResultDTO bye = MiniGameResultDTO.builder()
                .pin(pin)
                .token("bye")
                .build();

        service.saveResult(hello);
        service.saveResult(bye);

        assertThat(v0MemberRepository.count()).isEqualTo(2);
        assertThat(flavorRepository.count()).isEqualTo(0);
        assertThat(excludeRepository.count()).isEqualTo(1);
        assertThat(nationRepository.count()).isEqualTo(2);

        service.deleteGroup(pin);
        assertThat(v0GroupRepository.count()).isEqualTo(0);
        assertThat(v0MemberRepository.count()).isEqualTo(0);
        assertThat(flavorRepository.count()).isEqualTo(0);
        assertThat(excludeRepository.count()).isEqualTo(0);
        assertThat(nationRepository.count()).isEqualTo(0);
    }


    private MiniGameResultDTO makeDTO(int pin) {
        MiniGameResultDTO hello = MiniGameResultDTO.builder()
                .pin(pin)
                .token("hello")
                .build();
        hello.getExcludeNames().add(ExcludeName.GUT);
        hello.getNationNames().add(NationName.KOR);
        hello.getNationNames().add(NationName.EXO);
        return hello;
    }
}