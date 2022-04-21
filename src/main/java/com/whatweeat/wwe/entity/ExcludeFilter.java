package com.whatweeat.wwe.entity;

import com.whatweeat.wwe.entity.enums.ExcludeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class ExcludeFilter {
    @Id @GeneratedValue
    @Column(name = "EXCLUDE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXCLUDE_NAME")
    private ExcludeName excludeName;

    public ExcludeFilter(Menu menu, ExcludeName excludeName) {
        this.menu = menu;
        this.excludeName = excludeName;
    }
}
