package com.whatweeat.wwe.group.domain;

import com.whatweeat.wwe.common.event.Events;
import com.whatweeat.wwe.member.domain.MemberToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.whatweeat.wwe.group.domain.GroupStatus.DONE;
import static com.whatweeat.wwe.group.domain.GroupStatus.PENDING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "groups")
@NoArgsConstructor(access = PROTECTED)
@Getter
@EntityListeners(value = AuditingEntityListener.class)
@Slf4j
public class Group {
    @EmbeddedId
    private GroupPinNumber pinNumber;

    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @CreatedDate
    private LocalDateTime createdDate;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "token", column = @Column(name = "host_token"))
    )
    private MemberToken host;

    @ElementCollection
    private final Set<MemberToken> memberTokens = new HashSet<>();

    public void join(MemberToken joinMember) {
        groupValidationCheck();

        // TODO : 이미 참여한 멤버,
        //  기존 결과 무효 이벤트 처리(pinNumber, token)
        if (this.memberTokens.contains(joinMember)) {
            log.warn("NOT IMPLEMENTED YET");
        }
        this.memberTokens.add(joinMember);
    }

    private void groupValidationCheck() {
        if (!isJoinAble())
            throw new GroupInvalidException("미니게임이 이미 종료됐음.");
    }

    public void makeGroupInvalid(){
        groupValidationCheck();
        groupStatus = DONE;
    }

    public void groupHostCheck(MemberToken memberToken) {
        if (this.getHost().equals(memberToken)) return;
        throw new NotAllowedMemberException("Host 만 가능함.");
    }

    public boolean isJoinAble() {
        return groupStatus.isPending();
    }

    public Group(GroupPinNumber pinNumber, MemberToken host) {
        this.pinNumber = pinNumber;
        this.groupStatus = PENDING;
        this.host = host;
        this.memberTokens.add(host);
        Events.raise(new GroupCreationEvent(pinNumber.getPinNumber(), host.getToken()));
    }
}
