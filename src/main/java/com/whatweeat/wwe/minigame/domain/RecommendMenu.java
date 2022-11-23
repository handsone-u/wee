package com.whatweeat.wwe.minigame.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class RecommendMenu {
    @Id @GeneratedValue
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MiniGameGroup miniGameGroup;

    private String menuName;

    @Column(name = "MENU_URL")
    private String menuURL;
    private Double point;

    @ElementCollection
    @OrderColumn
    private List<String> keywords = new ArrayList<>();

    void setMiniGameGroup(MiniGameGroup miniGameGroup) {
        this.miniGameGroup = miniGameGroup;
    }

    public RecommendMenu(String menuName, String menuURL, Double point, List<String> keywords) {
        this.menuName = menuName;
        this.menuURL = menuURL;
        this.point = point;
        this.keywords = keywords;
    }
}
