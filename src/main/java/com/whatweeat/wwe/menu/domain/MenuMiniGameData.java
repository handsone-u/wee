package com.whatweeat.wwe.menu.domain;

import com.whatweeat.wwe.minigame.domain.form.ExpenseName;
import com.whatweeat.wwe.minigame.domain.form.FlavorName;
import com.whatweeat.wwe.minigame.domain.form.NationName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuMiniGameData {
    private Boolean rice;
    private Boolean noodle;
    private Boolean soup;
    private Boolean health;
    private Boolean alcohol;
    private Boolean instant;

    @Enumerated(EnumType.STRING)
    private ExpenseName expense;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<FlavorName> flavors = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<NationName> nations = new HashSet<>();

    public static MenuMiniGameData of(Boolean rice, Boolean noodle, Boolean soup, Boolean health,
                                      Boolean alcohol, Boolean instant, ExpenseName expense, Set<FlavorName> flavors,
                                      Set<NationName> nations) {
        return new MenuMiniGameData(rice, noodle, soup, health,
                alcohol, instant, expense, flavors,
                nations);
    }

}
