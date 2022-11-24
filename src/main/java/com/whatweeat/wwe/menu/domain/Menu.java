package com.whatweeat.wwe.menu.domain;

import com.whatweeat.wwe.minigame.domain.form.MiniGameForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    private String menuName;
    private String menuImage;
    private Integer frequency;

    @Embedded
    private MenuMiniGameData menuData;

    public void increaseFrequency() {
        this.frequency++;
    }

    public Menu(String menuName, String menuImage, MenuMiniGameData menuData) {
        this.menuName = menuName;
        this.menuImage = menuImage;
        this.frequency = 0;
        this.menuData = menuData;
    }

    public Menu(String menuName, String menuImage, Integer frequency, MenuMiniGameData menuData) {
        this(menuName, menuImage, menuData);
        this.frequency = frequency;
    }
}
