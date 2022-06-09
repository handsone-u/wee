package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MiniGameResultDTO;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.entity.mini_game_v0.V0Group;
import com.whatweeat.wwe.entity.mini_game_v0.V0Member;
import com.whatweeat.wwe.repository.mini_game_v0.*;
import com.whatweeat.wwe.service.mini_game_v0.MiniGameV0ServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class V0GroupServiceCRUDTest {
    @Autowired
    V0GroupRepository v0GroupRepository;
    @Autowired
    V0MemberRepository v0MemberRepository;
    @Autowired
    V0FlavorRepository v0flavorRepository;
    @Autowired
    V0ExcludeRepository v0excludeRepository;
    @Autowired
    V0NationRepository v0nationRepository;

    @Test
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
        assertThat(v0flavorRepository.count()).isEqualTo(0);
        assertThat(v0excludeRepository.count()).isEqualTo(1);
        assertThat(v0excludeRepository.findAll()).extracting("excludeName")
                .containsExactly(FlavorName.INTESTINE);
        assertThat(v0nationRepository.count()).isEqualTo(2);
        assertThat(v0nationRepository.findAll()).extracting("nationName")
                .containsOnly(NationName.KOREAN, NationName.EXOTIC);

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
        assertThat(v0flavorRepository.count()).isEqualTo(0);
        assertThat(v0excludeRepository.count()).isEqualTo(1);
        assertThat(v0nationRepository.count()).isEqualTo(2);

        service.deleteGroup(pin);
        assertThat(v0GroupRepository.count()).isEqualTo(0);
        assertThat(v0MemberRepository.count()).isEqualTo(0);
        assertThat(v0flavorRepository.count()).isEqualTo(0);
        assertThat(v0excludeRepository.count()).isEqualTo(0);
        assertThat(v0nationRepository.count()).isEqualTo(0);
    }

    @Test @DisplayName("멤버 제거")
    void deleteMember() {
        MiniGameService service = new MiniGameV0ServiceImpl(v0GroupRepository, v0MemberRepository);

        int pinNum = service.createGroup();
        assertThat(v0GroupRepository.count()).isEqualTo(1);
        assertThat(v0MemberRepository.count()).isEqualTo(0);

        MiniGameResultDTO dto = makeDTO(pinNum);
        V0Group group = service.saveResult(dto);
        V0Member member = group.getMembers().get(0);
        assertThat(v0MemberRepository.count()).isEqualTo(1);
        assertThat(group.getMembers().size()).isEqualTo(1);
        assertThat(v0excludeRepository.count()).isNotEqualTo(0);
        assertThat(v0nationRepository.count()).isNotEqualTo(0);
        assertThat(v0flavorRepository.count()).isEqualTo(0);

        System.out.println("DELETE!!!");
        service.deleteMember(member.getToken(), group.getId());
        group = v0GroupRepository.findById(group.getId()).get();

        assertThat(group.getMembers().size()).isEqualTo(0);
        assertThat(v0MemberRepository.count()).isEqualTo(0);
        assertThat(v0excludeRepository.count()).isEqualTo(0);
        assertThat(v0nationRepository.count()).isEqualTo(0);
        assertThat(v0flavorRepository.count()).isEqualTo(0);
    }

    private MiniGameResultDTO makeDTO(int pin) {
        MiniGameResultDTO hello = MiniGameResultDTO.builder()
                .pin(pin)
                .token("hello")
                .build();
        hello.getExcludeNames().add(FlavorName.INTESTINE);
        hello.getNationNames().add(NationName.KOREAN);
        hello.getNationNames().add(NationName.EXOTIC);
        return hello;
    }
}