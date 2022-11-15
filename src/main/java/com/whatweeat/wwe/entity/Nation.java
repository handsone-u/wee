package com.whatweeat.wwe.entity;

import com.whatweeat.wwe.minigame.domain.form.NationName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Nation {
    @Id @GeneratedValue
    @Column(name ="NATION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MINI_V0_ID")
    private MiniGameV0 miniGameV0;

    @Enumerated(EnumType.STRING)
    @Column(name = "NATION_NAME")
    private NationName nationName;

    public Nation(MiniGameV0 miniGameV0, NationName nationName) {
        this.miniGameV0 = miniGameV0;
        this.nationName = nationName;
    }
}
