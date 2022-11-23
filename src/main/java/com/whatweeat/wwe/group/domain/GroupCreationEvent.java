package com.whatweeat.wwe.group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class GroupCreationEvent {
    private Integer pinNumber;
    private String hostToken;
}
