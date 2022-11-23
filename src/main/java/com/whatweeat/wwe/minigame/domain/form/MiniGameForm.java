package com.whatweeat.wwe.minigame.domain.form;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MiniGameForm {
    @Embedded
    private Options options;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<FlavorName> excludeFlavors = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<NationName> nations = new HashSet<>();

    static public MiniGameForm of(Options options, Set<FlavorName> excludeFlavors, Set<NationName> nations) {
        return new MiniGameForm(options, excludeFlavors, nations);
    }
}
