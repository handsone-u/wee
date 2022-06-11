package com.whatweeat.wwe.service.mini_game_v0;

import com.whatweeat.wwe.controller.request.ResultSubmission;
import com.whatweeat.wwe.dto.MenuPoint;
import com.whatweeat.wwe.entity.Flavor;
import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.MiniGameV0;
import com.whatweeat.wwe.entity.Nation;
import com.whatweeat.wwe.entity.enums.ExpenseName;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import com.whatweeat.wwe.entity.mini_game_v0.V0Exclude;
import com.whatweeat.wwe.entity.mini_game_v0.V0Group;
import com.whatweeat.wwe.entity.mini_game_v0.V0Member;
import com.whatweeat.wwe.entity.mini_game_v0.V0Nation;
import com.whatweeat.wwe.repository.mini_game_v0.V0GroupRepository;
import com.whatweeat.wwe.repository.mini_game_v0.V0MemberRepository;
import com.whatweeat.wwe.service.MenuService;
import com.whatweeat.wwe.service.MiniGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor @Slf4j
@Transactional
// TODO : Feat,RuntimeException -> 사용자 정의 예외
public class MiniGameV0ServiceImpl implements MiniGameService {
    private final MenuService menuServiceImpl;

    private final V0GroupRepository v0GroupRepository;
    private final V0MemberRepository v0MemberRepository;
    private final int BOUND = 10000;
    private final int LOOP_MAX = 1000;

    private final double POSITIVE = 1;
    private final double NEGATIVE = -1;

    public int createGroup() {
        int id = generatePinNum();
        V0Group v0Group = v0GroupRepository.save(new V0Group(id));

        return v0Group.getId();
    }

    public V0Group saveResult(ResultSubmission dto) {
        V0Group group = v0GroupRepository.findById(dto.getPinNumber())
                .orElseThrow(() -> new RuntimeException()); // 없는 그룹 예외

        return saveGroup(dto, group);
    }

    private V0Group saveGroup(ResultSubmission dto, V0Group group) {
        V0Member member = dto.toV0Member();
        member = saveMember(dto, member);

        group.addMember(member);
        return v0GroupRepository.save(group);
    }

    private V0Member saveMember(ResultSubmission dto, V0Member member) {
        log.info("MEMBER SAVE Token = [{}]", member.getToken());
        saveExcludes(dto.getDislikedFoods(), member);
        saveNations(dto.getGameAnswer().getNation(), member);

        return v0MemberRepository.save(member);
    }

    private void saveExcludes(Set<FlavorName> excludeNames, V0Member member) {
        for (FlavorName excludeName : excludeNames) {
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

    @Transactional(readOnly = true)
    public List<MenuPoint> getGroupResult(int pin) {
        log.debug("GROUP PIN:[{}] 추론 시작",pin);

        // 그룹 조회
        V0Group v0Group = v0GroupRepository.findById(pin)
                .orElseThrow(() -> new RuntimeException()); // 없는 그룹 예외
        List<V0Member> members = v0Group.getMembers();

        // 못 먹는 음식 제외 조회
        Set<FlavorName> groupExclude = new HashSet<>();
        members.forEach(m -> m.getExcludes().forEach(
                exclude -> groupExclude.add(exclude.getExcludeName())));
        List<Menu> menus = menuServiceImpl.findAllExceptFlavorNames(groupExclude);

        List<MenuPoint> result = new ArrayList<>(menus.size());
        for (Menu menu : menus) { // N
            String menuName = menu.getMenuName();
            String menuURL = menu.getMenuImage();
            double point = 0;

            for (V0Member memberResult : members) { // M
                if(!memberResult.getComplete()) continue;
                point += calculateV0(menu.getMiniGameV0(), memberResult);
            }
            log.debug("MENU Name:[{}] Point=[{}]", menuName, point);
            result.add(new MenuPoint(menuName, menuURL, point));
        }
        result.sort(Comparator.reverseOrder());
        return result;
    }

    private double calculateV0(MiniGameV0 menuV0, V0Member member) {
        double point = 0;
        point += hangover(menuV0, member);
        point += greasy(menuV0, member);
        point += health(menuV0, member);
        point += alcohol(menuV0, member);
        point += instant(menuV0, member);
        point += spicy(menuV0, member);
        point += rich(menuV0, member);
        point += riceNoodleSoup(menuV0, member);
        point += nation(menuV0, member);
        return point;
    }

    private double hangover(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if(member.getHangover()==null) {
            log.trace("KEYWORD1 해장 상관없음...");
            return point;
        }
        Set<FlavorName> flavorNames = menu.getFlavors().stream()
                .map(Flavor::getFlavorName)
                .collect(Collectors.toSet());
        if(flavorNames.containsAll(Set.of(FlavorName.COOL, FlavorName.HOT)))
            point += 2 * (member.getHangover() ? POSITIVE : NEGATIVE);
        else if(flavorNames.contains(FlavorName.COOL)||flavorNames.contains(FlavorName.HOT))
            point += member.getHangover() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD1 해장 점수:[{}]", point);
        return point;
    }

    private double greasy(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getGreasy() == null) {
            log.trace("KEYWORD2 기름칠 상관없음...");
            return point;
        }
        Set<FlavorName> flavorNames = menu.getFlavors().stream()
                .map(Flavor::getFlavorName)
                .collect(Collectors.toSet());
        if(flavorNames.contains(FlavorName.GREASY))
            point += member.getGreasy() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD2 기름칠 점수:[{}]", point);
        return point;
    }

    private double health(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getHealth() == null) {
            log.trace("KEYWORD3 건강 상관없음...");
            return point;
        }
        point += menu.getHealthy() == member.getHealth() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD3 건강 점수:[{}]", point);
        return point;
    }

    private double alcohol(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getAlcohol() == null) {
            log.trace("KEYWORD4 안주 상관없음...");
            return point;
        }
        point += menu.getAlcohol() == member.getAlcohol() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD4 안주 점수:[{}]", point);
        return point;
    }

    private double instant(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getInstant() == null) {
            log.trace("KEYWORD5 간편 상관없음...");
            return point;
        }
        point += menu.getInstant() == member.getInstant() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD5 간편 점수:[{}]", point);
        return point;
    }

    private double spicy(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getSpicy() == null) {
            log.trace("KEYWORD6 매콤 상관없음...");
            return point;
        }
        Set<FlavorName> flavorNames = menu.getFlavors().stream()
                .map(Flavor::getFlavorName)
                .collect(Collectors.toSet());
        if(flavorNames.contains(FlavorName.SPICY))
            point += member.getSpicy() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD6 매콤 점수:[{}]", point);
        return point;
    }

    private double rich(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getRich() == null) {
            log.trace("KEYWORD7 돈 걱정 상관 없음...");
        }
        if(menu.getExpenseName().equals(ExpenseName.EXPENSIVE2))
            point += 2 * (member.getRich() ? POSITIVE : NEGATIVE);
        else if(menu.getExpenseName().equals(ExpenseName.EXPENSIVE1))
            point += member.getRich() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD7 돈 걱정 점수:[{}]", point);
        return point;
    }

    private double riceNoodleSoup(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getRice() == null && member.getNoodle() == null && member.getSoup() == null) {
            log.trace("KEYWORD8 밥면국 상관없음...");
            return point;
        }
        point += menu.getRice() == member.getRice() ? POSITIVE : NEGATIVE;
        point += menu.getNoodle() == member.getNoodle() ? POSITIVE : NEGATIVE;
        point += menu.getSoup() == member.getSoup() ? POSITIVE : NEGATIVE;
        log.trace("KEYWORD8 밥면국 점수:[{}]", point);
        return point;
    }

    private double nation(MiniGameV0 menu, V0Member member) {
        double point = 0;
        if (member.getNations().isEmpty()) {
            log.trace("KEYWORD9 음식 종류 상관없음...");
            return point;
        }
        Set<NationName> menuSet = menu.getNations().stream()
                .map(Nation::getNationName)
                .collect(Collectors.toSet());
        Set<NationName> memberSet = member.getNations().stream()
                .map(V0Nation::getNationName)
                .collect(Collectors.toSet());
        menuSet.retainAll(memberSet);
        point += menuSet.size();
        log.trace("KEYWORD9 음식 종류 점수:[{}]", point);
        return point;
    }

    public void deleteGroup(int pin) {
        V0Group v0Group = v0GroupRepository.findById(pin)
                .orElseThrow(() -> new RuntimeException());

        v0GroupRepository.delete(v0Group);
    }

    public void deleteMember(String token, Integer pin) {
        V0Group v0Group = v0GroupRepository.findById(pin)
                .orElseThrow(() -> new RuntimeException()); // 없는 그룹 예외
        V0Member v0Member = v0MemberRepository.findByTokenAndGroup(token, v0Group)
                .orElseThrow(() -> new RuntimeException()); // 없는 맴버 예외

        v0Group.removeMember(v0Member);
    }

    public boolean pinValidCheck(int id) {
        return v0GroupRepository.findById(id).isPresent();
    }

    public int countMember(int id) {
        V0Group v0Group = v0GroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException()); // 없는 그룹 예외
        return v0Group.getMembers().size();
    }

    public int countCompleteMember(int id) {
        V0Group v0Group = v0GroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException()); // 없는 그룹 예외
        return (int) v0Group.getMembers().stream()
                .filter(V0Member::getComplete)
                .count();
    }

    private int generatePinNum() {
        // TODO: VALID, PIN 갯수 이상의 group 있다면 생성 불가능 할것
        int groupCount = (int) v0GroupRepository.count();
        log.debug("GROUP Total Count = [{}]", groupCount);

        Random random = new Random(LocalDateTime.now().hashCode());
        int total = random.nextInt(BOUND);
        int count = 0;
        while (v0GroupRepository.findById(total).isPresent()) {
            log.warn("PIN NUM : [{}] DUP, GENERATING NEW NUM", total);
            total = random.nextInt(BOUND);
            count++;
        }
        if(count>=LOOP_MAX) total = v0GroupRepository.getMaxId() + 1;

        log.debug("GROUP CREATED... PIN = [{}]", total);
        return total;
    }
}
