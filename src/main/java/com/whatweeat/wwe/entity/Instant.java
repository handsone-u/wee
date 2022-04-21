package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Instant {
    @Id @GeneratedValue
    @Column(name = "INSTANT_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "IS_INSTANT")
    private Boolean isInstant;

    public Instant(Menu menu, Boolean isInstant) {
        this.menu = menu;
        this.isInstant = isInstant;
    }
}
