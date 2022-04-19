package com.whatweeat.wee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Alcohol {
    @Id @GeneratedValue
    @Column(name = "ALCOHOL_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "IS_ALCOHOL")
    private Boolean isAlcohol;

    public Alcohol(Menu menu, Boolean isAlcohol) {
        this.menu = menu;
        this.isAlcohol = isAlcohol;
    }
}
