package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class Health {
    @Id @GeneratedValue
    @Column(name = "HEALTH_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "IS_HEALTHY")
    private Boolean isHealthy;

    public Health(Menu menu, Boolean isHealthy) {
        this.menu = menu;
        this.isHealthy = isHealthy;
    }
}
