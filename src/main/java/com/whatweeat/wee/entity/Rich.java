package com.whatweeat.wee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Rich {
    @Id @GeneratedValue
    @Column(name = "RICH_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "IS_RICH")
    private Boolean isRich;

    public Rich(Menu menu, Boolean isRich) {
        this.menu = menu;
        this.isRich = isRich;
    }
}
