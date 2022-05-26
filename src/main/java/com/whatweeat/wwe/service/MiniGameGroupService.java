package com.whatweeat.wwe.service;

import com.whatweeat.wwe.entity.MiniGameGroup;
import com.whatweeat.wwe.entity.MiniGameMember;
import com.whatweeat.wwe.repository.MiniGameGroupRepository;
import com.whatweeat.wwe.repository.MiniGameMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor @Slf4j
@Transactional
public class MiniGameGroupService {
    private final MiniGameGroupRepository miniGameGroupRepository;
    private final MiniGameMemberRepository miniGameMemberRepository;
    private static int BOUND = 10000;
    private static int LOOP_MAX = 1000;

    public int createGroup() {
        int id = generatePinNum();
        MiniGameGroup miniGameGroup = new MiniGameGroup(id);
        miniGameGroup = miniGameGroupRepository.save(miniGameGroup);

        return miniGameGroup.getId();
    }

    public boolean pinValidCheck(int id) {
        return miniGameGroupRepository.findById(id).isPresent();
    }

    public int countMember(int id) {
        MiniGameGroup miniGameGroup = miniGameGroupRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException());
        return miniGameGroup.getMembers().size();
    }

    public int countCompleteMember(int id) {
        MiniGameGroup miniGameGroup = miniGameGroupRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException());
        return (int) miniGameGroup.getMembers().stream()
                .filter(MiniGameMember::getComplete)
                .count();
    }

    private int generatePinNum() {
        Random random = new Random(LocalDateTime.now().hashCode());
        int id = random.nextInt(BOUND);
        int count = 0;
        while (miniGameGroupRepository.findById(id).isPresent()) {
            log.warn("PIN NUM : [{}] DUP, GENERATING NEW NUM", id);
            id = random.nextInt(BOUND);
            count++;
        }
        if(count>=LOOP_MAX) id = miniGameGroupRepository.getMaxId() + 1;
        return id;
    }
}
