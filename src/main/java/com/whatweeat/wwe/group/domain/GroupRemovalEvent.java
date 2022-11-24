package com.whatweeat.wwe.group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class GroupRemovalEvent {
    private Integer pinNumber;
}
