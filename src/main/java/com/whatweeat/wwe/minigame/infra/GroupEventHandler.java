package com.whatweeat.wwe.minigame.infra;

import com.whatweeat.wwe.group.domain.GroupCreationEvent;
import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.group.domain.GroupRemovalEvent;
import com.whatweeat.wwe.minigame.application.MiniGameCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupEventHandler {
    private final MiniGameCommandService miniGameCommandService;

    @TransactionalEventListener(
            classes = GroupCreationEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleGroupCreationEvent(GroupCreationEvent event) {
        log.info("GroupCreationEvent raised!");
        GroupPinNumber group = miniGameCommandService.createMiniGameGroup(event.getPinNumber(), event.getHostToken());
        log.info("MiniGameGroup {} created!", group.getPinNumber());
    }

    @TransactionalEventListener(
            classes = GroupRemovalEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleGroupRemovalEvent(GroupRemovalEvent event) {

        log.info("GroupRemovalEvent raised!");
        GroupPinNumber groupPinNumber = miniGameCommandService.removeMiniGameGroup(event.getPinNumber());
        log.info("MiniGameGroup {} created!", groupPinNumber.getPinNumber());
    }
}
