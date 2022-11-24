package com.whatweeat.wwe.minigame.application;

import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.member.domain.MemberToken;
import com.whatweeat.wwe.minigame.domain.MiniGameGroup;
import com.whatweeat.wwe.minigame.domain.MiniGameMember;
import com.whatweeat.wwe.minigame.domain.MiniGameMemberId;
import com.whatweeat.wwe.minigame.domain.MiniGameRepository;
import com.whatweeat.wwe.minigame.domain.form.MiniGameForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MiniGameCommandService {

    private final MiniGameRepository miniGameRepository;

    @Transactional
    public GroupPinNumber createMiniGameGroup(int pinNumber, String hostToken) {
        MiniGameGroup miniGameGroup = new MiniGameGroup(GroupPinNumber.of(pinNumber), MemberToken.of(hostToken));
        MiniGameGroup save = miniGameRepository.save(miniGameGroup);
        return save.getPinNumber();
    }

    @Transactional
    public MiniGameMemberId addMiniGameMember(MiniGameMemberRequest request) {
        MiniGameGroup miniGameGroup = findMiniGameGroup(Integer.parseInt(request.getPinNumber()));
        MiniGameMemberId id = MiniGameMemberId.of(miniGameGroup.getPinNumber(), MemberToken.of(request.getToken()));
        MiniGameForm miniGameForm = request.toForm();
        MiniGameMember miniGameMember = new MiniGameMember(id, miniGameForm);
        miniGameGroup.addMiniGameMember(miniGameMember);
        return miniGameMember.getId();
    }

    @Transactional
    public GroupPinNumber removeMiniGameMember(int pinNumber, String memberToken) {
        MiniGameGroup miniGameGroup = findMiniGameGroup(pinNumber);
        miniGameGroup.getMiniGameMembers()
                .removeIf(m -> m.getId().getMemberToken().equals(MemberToken.of(memberToken)));
        return miniGameGroup.getPinNumber();
    }

    @Transactional
    public GroupPinNumber removeMiniGameGroup(int pinNumber) {
        MiniGameGroup miniGameGroup = findMiniGameGroup(pinNumber);
        miniGameRepository.delete(miniGameGroup);
        return miniGameGroup.getPinNumber();
    }

    private MiniGameGroup findMiniGameGroup(int pinNumber) {
        // TODO : 예외 처리
        return miniGameRepository.findById(GroupPinNumber.of(pinNumber)).orElseThrow(RuntimeException::new);
    }
}
