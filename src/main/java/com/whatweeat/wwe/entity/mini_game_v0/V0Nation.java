package com.whatweeat.wwe.entity.mini_game_v0;

import com.whatweeat.wwe.entity.enums.NationName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class V0Nation {
    @Id @GeneratedValue
    @Column(name = "NATION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private V0Member member;

    @Enumerated
    private NationName nationName;

    public V0Nation(V0Member member, NationName nationName) {
        this.member = member;
        this.nationName = nationName;
    }
}
