package com.whatweeat.wwe.minigame.domain;

import com.whatweeat.wwe.minigame.domain.form.MiniGameForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class MiniGameMember {

    @EmbeddedId
    private MiniGameMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MiniGameGroup miniGameGroup;

    @Embedded
    private MiniGameForm miniGameForm;

    void setMiniGameGroup(MiniGameGroup miniGameGroup) {
        this.miniGameGroup = miniGameGroup;
    }

    void setMiniGameForm(MiniGameForm miniGameForm) {
        this.miniGameForm = miniGameForm;
    }

    public MiniGameMember(MiniGameMemberId id, MiniGameForm miniGameForm) {
        this.id = id;
        this.miniGameForm = miniGameForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiniGameMember that = (MiniGameMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
