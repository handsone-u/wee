package com.whatweeat.wee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @Getter
public class Menu {
    @Id @GeneratedValue
    @Column(name = "MENU_ID")
    private Long id;

    @Column(name = "MENU_NAME")
    private String menuName;

    private Integer frequency;

    @OneToMany(mappedBy = "menu")
    private final List<Nation> nations = new ArrayList<>();

    @OneToMany(mappedBy = "menu")
    private final List<ExcludeFilter> excludeFilters = new ArrayList<>();

    @OneToMany(mappedBy = "menu")
    private final List<Flavor> flavors = new ArrayList<>();

    public Menu(String menuName) {
        this.menuName = menuName;
        this.frequency = 0;
    }

    public Menu(String menuName, Integer frequency) {
        this.menuName = menuName;
        this.frequency = frequency;
    }
}
