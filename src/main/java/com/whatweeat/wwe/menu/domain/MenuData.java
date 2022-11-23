package com.whatweeat.wwe.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MenuData {
    String menuName;
    String nations;
    String excludes;
    String isSoup;
    String isNoodle;
    String isRice;
    String isHealthy;
    String flavors;
    String isInstant;
    String isAlcohol;
    String expense;
    String menuImage;
}
