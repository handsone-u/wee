package com.whatweeat.wee.entity;

import com.whatweeat.wee.entity.enums.FlavorName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Flavor {
    @Id @GeneratedValue
    @Column(name = "FLAVOR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Enumerated(EnumType.STRING)
    @Column(name = "FLAVOR_NAME")
    private FlavorName flavorName;
}
