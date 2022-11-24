package com.whatweeat.wwe.minigame.domain;

import com.whatweeat.wwe.group.domain.Group;
import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.group.domain.GroupStatus;
import com.whatweeat.wwe.group.domain.NotAllowedMemberException;
import com.whatweeat.wwe.member.domain.MemberToken;
import com.whatweeat.wwe.minigame.domain.form.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MiniGameGroupDomainTest {

    @Test
    void createGroup() {
        // GIVEN
        int pinNumber = 1234;
        String hostToken = "host";
        Group group = createdGroup(pinNumber, hostToken);
        GroupPinNumber miniGameId = group.getPinNumber();

        // WHEN
        MiniGameGroup miniGameGroup = new MiniGameGroup(miniGameId, group.getHost());

        // THEN
        assertThat(miniGameGroup.getPinNumber())
                .isEqualTo(group.getPinNumber());
        assertThat(miniGameGroup.getHost())
                .isEqualTo(group.getHost());
        assertThat(miniGameGroup.getMiniGameMembers()).isEmpty();
        assertThat(miniGameGroup.getRecommendMenus()).isEmpty();
        assertThat(miniGameGroup.getMiniGameStatus()).isEqualTo(GroupStatus.PENDING);
    }

    @Test
    void addMember() {
        // GIVEN
        int pinNumber = 1234;
        String hostToken = "host";
        String member0Token = "member0";
        Group group = createdGroup(pinNumber, hostToken);
        MiniGameGroup miniGameGroup = new MiniGameGroup(group.getPinNumber(), group.getHost());

        // WHEN
        int size = 1;
        Options hostOptions = Options.of(true, true, true, true, true, false);
        ExpenseName hostExpense = ExpenseName.EXPENSIVE1;
        Set<FlavorName> preferFlavorNames = new HashSet<>(Arrays.asList(FlavorName.BLAND, FlavorName.COOL));
        Set<FlavorName> excludeNames = new HashSet<>(Arrays.asList(FlavorName.HOT, FlavorName.INTESTINE));
        Set<NationName> nationNames = new HashSet<>(Arrays.asList(NationName.KOREAN));
        MiniGameForm hostForm = MiniGameForm.of(hostOptions, hostExpense, preferFlavorNames, excludeNames, nationNames);
        MiniGameMemberId miniGameHostId = MiniGameMemberId.of(group.getPinNumber(), MemberToken.of(hostToken));
        MiniGameMember miniGameHost = new MiniGameMember(miniGameHostId, hostForm);

        miniGameGroup.addMiniGameMember(miniGameHost);

        // THEN
        Set<MiniGameMember> members = miniGameGroup.getMiniGameMembers();
        assertThat(miniGameGroup.getMiniGameStatus()).isEqualTo(GroupStatus.PENDING);
        assertThat(miniGameGroup.getMiniGameMembers().size()).isEqualTo(size);
        for (MiniGameMember member : members) {
            assertThat(member.getMiniGameGroup()).isEqualTo(miniGameGroup);
            assertThat(member.getMiniGameGroup().getHost()).isEqualTo((MemberToken.of(hostToken)));
            assertThat(member.getMiniGameGroup().getPinNumber()).isEqualTo(GroupPinNumber.of(pinNumber));
        }
    }

    @Test
    void recommendValidationCheck() {
        // GIVEN
        int pinNumber = 1234;
        String hostToken = "host";
        String member0Token = "member0";
        Group group = createdGroup(pinNumber, hostToken);
        MiniGameGroup miniGameGroup = new MiniGameGroup(group.getPinNumber(), group.getHost());

        // WHEN
        Throwable hostCase = catchThrowable(() -> miniGameGroup.initiateRecommendation(MemberToken.of(hostToken)));
        Throwable otherCase = catchThrowable(() -> miniGameGroup.initiateRecommendation(MemberToken.of(member0Token)));

        // THEN
        assertThat(hostCase).doesNotThrowAnyException();
        assertThat(otherCase).isInstanceOf(NotAllowedMemberException.class);
    }

    private Group createdGroup(Integer pinNumber, String hostToken) {
        GroupPinNumber groupPinNumber = GroupPinNumber.of(pinNumber);
        MemberToken host = MemberToken.of(hostToken);
        return new Group(groupPinNumber, host);
    }
}