package com.whatweeat.wwe.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum FlavorName {
    COOL("시원"),
    HOT("얼큰"),
    GREASY("느끼"),
    BLAND("담백");

    public static FlavorName lookup(String name) {
        for (FlavorName value : FlavorName.values()) {
            if(value.desc.equals(name))
                return value;
        }
        return null;
    }

    private final String desc;
}
