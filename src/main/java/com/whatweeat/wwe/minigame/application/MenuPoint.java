package com.whatweeat.wwe.minigame.application;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuPoint {
    private String menuName;
    private String menuURL;
    private Double point;
    private List<String> keywords = new ArrayList<>();

    public MenuPoint(String menuName, String menuURL, Double point, List<String> keywords) {
        this.menuName = menuName;
        this.menuURL = menuURL;
        this.point = point;
        this.keywords = keywords;
    }
}
