package com.whatweeat.wwe.group.domain;

import com.whatweeat.wwe.common.event.EventConfiguration;
import com.whatweeat.wwe.member.domain.MemberToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith({SpringExtension.class})
@Import(EventConfiguration.class)
@RecordApplicationEvents
class GroupDomainTest {

    @Autowired
    ApplicationEvents events;

    @Test
    void create() {
        // GIVEN
        GroupPinNumber groupPinNumber = GroupPinNumber.of(1234);
        MemberToken host = MemberToken.of("host");

        // WHEN
        Group group = new Group(groupPinNumber, host);

        // THEN
        assertThat(group.getPinNumber()).isEqualTo(groupPinNumber);
        assertThat(group.getHost()).isEqualTo(host);
        assertThat(group.getGroupStatus()).isEqualTo(GroupStatus.PENDING);
        assertThat(group.getMemberTokens().size()).isEqualTo(1);
        assertThat(group.getMemberTokens()).containsOnly(host);
        assertThat(events.stream(GroupCreationEvent.class).count()).isEqualTo(1);
    }

    @Test
    void deleteByHost() {
        // GIVEN
        GroupPinNumber groupPinNumber = GroupPinNumber.of(1234);
        MemberToken host = MemberToken.of("host");
        Group group = new Group(groupPinNumber, host);

        // WHEN
        Throwable throwable = catchThrowable(() -> group.groupHostCheck(host));

        // THEN
        assertThat(throwable).doesNotThrowAnyException();
    }

    @Test
    void deleteNotAllowedMember() {
        // GIVEN
        GroupPinNumber groupPinNumber = GroupPinNumber.of(1234);
        MemberToken host = MemberToken.of("host");
        MemberToken notAllowed = MemberToken.of("notAllowed");
        Group group = new Group(groupPinNumber, host);

        // WHEN
        Throwable throwable = catchThrowable(() -> group.groupHostCheck(notAllowed));

        // THEN
        assertThat(throwable).isInstanceOf(NotAllowedMemberException.class);
    }

    @Test
    void invalidGroupJoin() {
        // GIVEN
        GroupPinNumber groupPinNumber = GroupPinNumber.of(1234);
        Group group = new Group(groupPinNumber, MemberToken.of("host"));
        group.makeGroupInvalid();

        // WHEN
        Throwable throwable = catchThrowable(() -> group.join(MemberToken.of("another")));

        // THEN
        assertThat(group.getGroupStatus()).isEqualTo(GroupStatus.DONE);
        assertThat(throwable).isInstanceOf(GroupInvalidException.class);
    }

    @Test
    void join() {
        // GIVEN
        GroupPinNumber groupPinNumber = GroupPinNumber.of(1234);
        MemberToken host = MemberToken.of("host");
        Group group = new Group(groupPinNumber, host);

        // WHEN
        MemberToken joiner = MemberToken.of("join");
        group.join(joiner);

        // THEN
        assertThat(group.getPinNumber()).isEqualTo(groupPinNumber);
        assertThat(group.getHost()).isEqualTo(host);
        assertThat(group.getGroupStatus()).isEqualTo(GroupStatus.PENDING);
        assertThat(group.getMemberTokens().size()).isEqualTo(2);
        assertThat(group.getMemberTokens()).contains(host, joiner);
    }
}