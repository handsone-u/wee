package com.whatweeat.wwe.minigame.domain;

import com.whatweeat.wwe.group.domain.GroupInvalidException;
import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.group.domain.GroupStatus;
import com.whatweeat.wwe.group.domain.NotAllowedMemberException;
import com.whatweeat.wwe.member.domain.MemberToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED) @AllArgsConstructor(access = PROTECTED) @Getter
public class MiniGameGroup {

    @EmbeddedId
    @AttributeOverrides(
            @AttributeOverride(name = "pinNumber", column = @Column(name = "mini_game_pin_number"))
    )
    private GroupPinNumber pinNumber;

    private MemberToken host;

    @Enumerated(EnumType.STRING)
    private GroupStatus miniGameStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "miniGameGroup")
    private Set<MiniGameMember> miniGameMembers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "miniGameGroup")
    private List<RecommendMenu> recommendMenus = new ArrayList<>();

    void initiateRecommendation(MemberToken memberToken) {
        gameHostCheck(memberToken);
        gameValidationCheck();
        miniGameStatus = GroupStatus.RUNNING;
        // TODO : 미니게임 추천 로직,
        //  이벤트 처리
    }

    void saveRecommendationResult(List<RecommendMenu> recommendMenus) {
        for (RecommendMenu recommendMenu : recommendMenus)
            addMiniGameMenu(recommendMenu);
        this.miniGameStatus = GroupStatus.DONE;
    }

    void addMiniGameMenu(RecommendMenu recommendMenu) {
        this.recommendMenus.add(recommendMenu);
        recommendMenu.setMiniGameGroup(this);
    }

    public void addMiniGameMember(MiniGameMember miniGameMember) {
        gameValidationCheck();
        this.miniGameMembers.add(miniGameMember);
        miniGameMember.setMiniGameGroup(this);
    }

    private void gameHostCheck(MemberToken memberToken) {
        if (!host.equals(memberToken))
            throw new NotAllowedMemberException();
    }

    private void gameValidationCheck() {
        if(!miniGameStatus.isPending())
            throw new GroupInvalidException();
    }

    public MiniGameGroup(GroupPinNumber pinNumber, MemberToken host) {
        this.pinNumber = pinNumber;
        this.host = host;
        this.miniGameStatus = GroupStatus.PENDING;
    }
}
