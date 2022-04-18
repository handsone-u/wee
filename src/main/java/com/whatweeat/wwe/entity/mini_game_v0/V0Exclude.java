package com.whatweeat.wwe.entity.mini_game_v0;

import com.whatweeat.wwe.entity.enums.FlavorName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class V0Exclude {
    @Id @GeneratedValue
    @Column(name = "EXCLUDED_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private V0Member member;

    @Enumerated
    private FlavorName excludeName;

    public V0Exclude(V0Member member, FlavorName excludeName) {
        this.member = member;
        this.excludeName = excludeName;
    }
}
