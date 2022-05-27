package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MiniGameResultDTO;
import com.whatweeat.wwe.entity.enums.ExcludeName;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.entity.mini_game_v0.*;
import com.whatweeat.wwe.repository.V0GroupRepository;
import com.whatweeat.wwe.repository.V0MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor @Slf4j
@Transactional
public class miniGameV0Service {
    private final V0GroupRepository v0GroupRepository;
    private final V0MemberRepository v0MemberRepository;
    private final int BOUND = 10000;
    private final int LOOP_MAX = 1000;

    public int createGroup() {
        int id = generatePinNum();
        V0Group v0Group = new V0Group(id);
        v0Group = v0GroupRepository.save(v0Group);

        return v0Group.getId();
    }

    public V0Group saveResult(MiniGameResultDTO dto) {
        V0Group group = v0GroupRepository.findById(dto.getPin())
                .orElseThrow(() -> new RuntimeException());

        return saveGroup(dto, group);
    }

    private V0Group saveGroup(MiniGameResultDTO dto, V0Group group) {
        V0Member member = new V0Member(dto.getToken(), true, group,
                dto.getRice(), dto.getNoodle(), dto.getSoup(), dto.getHealthy(), dto.getInstant(), dto.getAlcohol(),
                dto.getExpenseName());
        member = saveMember(dto, member);

        group.addMember(member);
        return v0GroupRepository.save(group);
    }

    private V0Member saveMember(MiniGameResultDTO dto, V0Member member) {
        saveFlavors(dto.getFlavorNames(), member);
        saveExcludes(dto.getExcludeNames(), member);
        saveNations(dto.getNationNames(), member);

        return v0MemberRepository.save(member);
    }

    private void saveFlavors(Set<FlavorName> flavorNames, V0Member member) {
        for (FlavorName flavorName : flavorNames) {
            V0Flavor flavor = new V0Flavor(member, flavorName);
            member.addFlavor(flavor);
        }
    }
    private void saveExcludes(Set<ExcludeName> excludeNames, V0Member member) {
        for (ExcludeName excludeName : excludeNames) {
            V0Exclude exclude = new V0Exclude(member, excludeName);
            member.addExclude(exclude);
        }
    }
    private void saveNations(Set<NationName> nationNames, V0Member member) {
        for (NationName nationName : nationNames) {
            V0Nation nation = new V0Nation(member, nationName);
            member.addNation(nation);
        }
    }

    public void getGroupResult(int pin) {
        V0Group v0Group = v0GroupRepository.findById(pin)
                .orElseThrow(() -> new RuntimeException());
        List<V0Member> members = v0Group.getMembers();
        // TODO: FEAT
        // 그룹 미니게임 추론 로직
    }

    public void deleteGroup(int pin) {
        V0Group v0Group = v0GroupRepository.findById(pin)
                .orElseThrow(() -> new RuntimeException());

        v0GroupRepository.delete(v0Group);
    }

    public boolean pinValidCheck(int id) {
        return v0GroupRepository.findById(id).isPresent();
    }

    public int countMember(int id) {
        V0Group v0Group = v0GroupRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException());
        return v0Group.getMembers().size();
    }

    public int countCompleteMember(int id) {
        V0Group v0Group = v0GroupRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException());
        return (int) v0Group.getMembers().stream()
                .filter(V0Member::getComplete)
                .count();
    }

    private int generatePinNum() {
        // TODO: VALID
        // PIN 갯수 이상의 group 있다면 생성 불가능 할것.
        int groupCount = (int) v0GroupRepository.count();

        Random random = new Random(LocalDateTime.now().hashCode());
        int id = random.nextInt(BOUND);
        int count = 0;
        while (v0GroupRepository.findById(id).isPresent()) {
            log.warn("PIN NUM : [{}] DUP, GENERATING NEW NUM", id);
            id = random.nextInt(BOUND);
            count++;
        }
        if(count>=LOOP_MAX) id = v0GroupRepository.getMaxId() + 1;
        return id;
    }
}
