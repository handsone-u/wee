package com.whatweeat.wwe.minigame.domain.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum NationName {
    KOREAN("한식"),
    CONVENIENT("분식"),
    JAPANESE("일식"),
    CHINESE("중식"),
    WESTERN("양식"),
    EXOTIC("이국요리"),
    ETC("기타");

    public static NationName lookup(String name) {
        for (NationName value : NationName.values()) {
            if(value.desc.equals(name))
                return value;
        }
        return null;
    }

    private final String desc;
}
