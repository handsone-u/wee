package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "MENU_ID")
    private Long id;

    private String menuName;
    private String menuImage;
    private Integer frequency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MINI_V0_ID")
    private MiniGameV0 miniGameV0;
}
