package com.whatweeat.wwe.minigame.domain;

import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.member.domain.MemberToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class MiniGameMemberId implements Serializable {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "pinNumber", column = @Column(name = "mini_game_pin_number"))
    )
    private GroupPinNumber groupPinNumber;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "token", column = @Column(name = "mini_game_member_token"))
    )
    private MemberToken memberToken;

    public static MiniGameMemberId of(GroupPinNumber groupPinNumber, MemberToken memberToken) {
        return new MiniGameMemberId(groupPinNumber, memberToken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiniGameMemberId that = (MiniGameMemberId) o;
        return Objects.equals(groupPinNumber, that.groupPinNumber) && Objects.equals(memberToken, that.memberToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupPinNumber, memberToken);
    }
}
