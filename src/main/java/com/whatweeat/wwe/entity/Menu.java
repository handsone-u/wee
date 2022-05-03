package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @Getter @Setter
public class Menu {
    @Id @GeneratedValue
    @Column(name = "MENU_ID")
    private Long id;

    @Column(name = "MENU_NAME")
    private String menuName;

    private Integer frequency;

    @OneToOne(mappedBy = "menu", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Style style;
    @OneToOne(mappedBy = "menu", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Alcohol alcohol;
    @OneToOne(mappedBy = "menu", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Health health;
    @OneToOne(mappedBy = "menu", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Instant instant;
    @OneToOne(mappedBy = "menu", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Rich rich;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private final List<Nation> nations = new ArrayList<>();
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private final List<ExcludeFilter> excludeFilters = new ArrayList<>();
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private final List<Flavor> flavors = new ArrayList<>();

    public void addNation(Nation nation) {
        nations.add(nation);
    }
    public void addExcludeFilter(ExcludeFilter excludeFilter) {
        excludeFilters.add(excludeFilter);
    }
    public void addFlavor(Flavor flavor) {
        flavors.add(flavor);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                '}';
    }

    public Menu(String menuName) {
        this.menuName = menuName;
        this.frequency = 0;
    }

    public Menu(String menuName, Integer frequency) {
        this.menuName = menuName;
        this.frequency = frequency;
    }
}
