package com.whatweeat.wwe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MiniGameResultV0 {
    private Boolean rice;
    private Boolean noodle;
    private Boolean soup;
    private Boolean healthy;
    private Boolean instant;
    private Boolean alcohol;
    private String expenseValue;
    private String flavorValues;
    private String excludeValues;
    private String nationValues;
}
