package com.whatweeat.wwe.entity;

import com.whatweeat.wwe.entity.enums.NationName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Nation {
    @Id @GeneratedValue
    @Column(name ="NATION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Enumerated(EnumType.STRING)
    @Column(name = "NATION_NAME")
    private NationName nationName;

    public Nation(Menu menu, NationName nationName) {
        this.menu = menu;
        this.nationName = nationName;
    }
}