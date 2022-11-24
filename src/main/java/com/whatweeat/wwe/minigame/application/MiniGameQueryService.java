package com.whatweeat.wwe.minigame.application;

import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.minigame.domain.MiniGameGroup;
import com.whatweeat.wwe.minigame.domain.MiniGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MiniGameQueryService {
    private final MiniGameRepository miniGameRepository;

    @Transactional(readOnly = true)
//    @Cacheable
    public List<MenuPoint> getGroupResult(Integer pinNumber) {
        GroupPinNumber groupPinNumber = GroupPinNumber.of(pinNumber);
        // TODO : 예외 처리
        //  없는 entity
        MiniGameGroup miniGameGroup = miniGameRepository.findById(groupPinNumber)
                .orElseThrow(RuntimeException::new);
        return miniGameGroup.getRecommendMenus()
                .stream()
                .map(rm -> new MenuPoint(rm.getMenuName(), rm.getMenuURL(), rm.getPoint(), rm.getKeywords()))
                .collect(Collectors.toList());
    }

}
