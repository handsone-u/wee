package com.whatweeat.wwe.entity;

import com.whatweeat.wwe.entity.enums.FlavorName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Flavor {
    @Id @GeneratedValue
    @Column(name = "FLAVOR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MINI_V0_ID")
    private MiniGameV0 miniGameV0;

    @Enumerated(EnumType.STRING)
    @Column(name = "FLAVOR")
    private FlavorName flavorName;

    public Flavor(MiniGameV0 miniGameV0, FlavorName flavorName) {
        this.miniGameV0 = miniGameV0;
        this.flavorName = flavorName;
    }
}
