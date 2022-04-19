package com.whatweeat.wee.entity;

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

    @Column(name = "IS_RICE")
    private Boolean isRice;
    @Column(name = "IS_SOUP")
    private Boolean isSoup;
    @Column(name = "IS_NOODLE")
    private Boolean isNoodle;

    public Style(Menu menu, Boolean isRice, Boolean isSoup, Boolean isNoodle) {
        this.menu = menu;
        this.isRice = isRice;
        this.isSoup = isSoup;
        this.isNoodle = isNoodle;
    }
}
