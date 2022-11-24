package com.whatweeat.wwe.group.application;

import com.whatweeat.wwe.common.event.Events;
import com.whatweeat.wwe.group.domain.Group;
import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.group.domain.GroupRemovalEvent;
import com.whatweeat.wwe.group.domain.GroupRepository;
import com.whatweeat.wwe.member.domain.MemberToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public boolean isValid(int pinNumber) {
        GroupPinNumber groupPinNumber = GroupPinNumber.of(pinNumber);
        Group group = findGroupById(groupPinNumber);
        return group.isJoinAble();
    }

    @Transactional
    public GroupPinNumber createGroup(String hostToken) {
        GroupPinNumber groupPinNumber = groupRepository.generateNextNumber();
        MemberToken host = MemberToken.of(hostToken);
        Group group = new Group(groupPinNumber, host);
        return groupRepository.save(group)
                .getPinNumber();
    }

    @Transactional
    public GroupPinNumber removeGroup(int pinNumber) {
        GroupPinNumber groupPinNumber = GroupPinNumber.of(pinNumber);
        Group group = findGroupById(groupPinNumber);
        groupRepository.delete(group);
        Events.raise(new GroupRemovalEvent(pinNumber));
        return group.getPinNumber();
    }

    private Group findGroupById(GroupPinNumber pinNumber) {
        // TODO : 없는 entity 조회,
        //  예외처리
        Group group = groupRepository.findById(pinNumber)
                .orElseThrow(() -> new RuntimeException());
        return group;
    }
}
