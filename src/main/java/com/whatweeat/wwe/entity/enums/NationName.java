package com.whatweeat.wwe.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum NationName {
    KOR("한식"),
    CONV("분식"),
    JAP("일식"),
    CHN("중식"),
    WEST("양식"),
    EXO("이국요리"),
    ETC("기타");

    public static NationName valueOfDesc(String name) {
        for (NationName value : NationName.values()) {
            if(value.desc.equals(name))
                return value;
        }
        return null;
    }

    private final String desc;
}
