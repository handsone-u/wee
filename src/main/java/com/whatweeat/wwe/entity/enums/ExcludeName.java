package com.whatweeat.wwe.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum ExcludeName {
    SPICY("매움"),
    SASHIMI("날것"),
    INTESTINE("내장"),
    SEAFOOD("해산물"),
    MEAT("고기");

    public static ExcludeName lookup(String name) {
        for (ExcludeName value : ExcludeName.values()) {
            if(value.desc.equals(name))
                return value;
        }
        return null;
    }

    private final String desc;
}
