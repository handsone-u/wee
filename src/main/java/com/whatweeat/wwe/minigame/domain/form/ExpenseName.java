package com.whatweeat.wwe.minigame.domain.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum ExpenseName {
    CHEAP("쌈"),
    EXPENSIVE1("비쌈1"),
    EXPENSIVE2("비쌈2"),;

    private final String desc;

    public static ExpenseName lookup(String name) {
        for (ExpenseName value : ExpenseName.values()) {
            if (value.desc.equals(name)) return value;
        }
        return null;
    }
}