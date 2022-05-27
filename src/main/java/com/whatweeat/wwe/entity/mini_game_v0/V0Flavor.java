package com.whatweeat.wwe.entity.mini_game_v0;

import com.whatweeat.wwe.entity.enums.FlavorName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class V0Flavor {
    @Id @GeneratedValue
    @Column(name = "FLAVOR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private V0Member member;

    @Enumerated
    private FlavorName flavorName;

    public V0Flavor(V0Member member, FlavorName flavorName) {
        this.member = member;
        this.flavorName = flavorName;
    }
}
