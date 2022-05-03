package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Style {
    @Id @GeneratedValue
    @Column(name = "STYLE_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "IS_SOUP")
    private Boolean isSoup;
    @Column(name = "IS_NOODLE")
    private Boolean isNoodle;
    @Column(name = "IS_RICE")
    private Boolean isRice;

    public Style(Menu menu, Boolean isSoup, Boolean isNoodle, Boolean isRice) {
        this.menu = menu;
        this.isSoup = isSoup;
        this.isNoodle = isNoodle;
        this.isRice = isRice;
    }
}
