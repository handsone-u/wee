package com.whatweeat.wwe.dto;

import com.whatweeat.wwe.entity.enums.ExcludeName;
import com.whatweeat.wwe.entity.enums.ExpenseName;
import com.whatweeat.wwe.entity.enums.FlavorName;
import com.whatweeat.wwe.entity.enums.NationName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class MenuCreateDTO {
    private String menuName;
    private String menuImage;
    private Boolean rice;
    private Boolean noodle;
    private Boolean soup;
    private Boolean healthy;
    private Boolean instant;
    private Boolean alcohol;
    private ExpenseName expenseName;
    private Set<FlavorName> flavorNames = new HashSet<>();
    private Set<ExcludeName> excludeNames = new HashSet<>();
    private Set<NationName> nationNames = new HashSet<>();

    private String expenseValue;
    private String flavorValues;
    private String excludeValues;
    private String nationValues;

    public void lookup() {
        expenseName = ExpenseName.lookup(expenseValue);
        String[] flavorSplit = flavorValues.split(",");
        String[] excludeSplit = excludeValues.split(",");
        String[] nationSplit = nationValues.split(",");
        for (String s : flavorSplit) flavorNames.add(FlavorName.lookup(s));
        for (String s : excludeSplit) excludeNames.add(ExcludeName.lookup(s));
        for (String s : nationSplit) nationNames.add(NationName.lookup(s));
    }
}
